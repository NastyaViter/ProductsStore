package com.netcracker.crm.dao.impl;

import com.netcracker.crm.dao.AbstractDao;
import com.netcracker.crm.dao.IEntityDao;
import com.netcracker.crm.dao.exception.DaoException;
import com.netcracker.crm.dao.rowmapper.AtributeValueRowMapper;
import com.netcracker.crm.dao.rowmapper.EAVlistRowMapper;
import com.netcracker.crm.dao.rowmapper.EntityRowMapper;
import com.netcracker.crm.entity.Atribute;
import com.netcracker.crm.entity.Entity;
import com.netcracker.crm.entity.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.netcracker.crm.dao.constants.DaoConstants.*;

/**
 * Created by on 12.11.2016.
 */

public class EntityDaoImpl extends AbstractDao<Entity> implements IEntityDao {


    public void addValue(List<Value> valuesArr, int idEntity) {
        final String sql = "INSERT INTO TBL_VALUE (" +
                COLUMN_VALUE_ID + ", " +
                COLUMN_VALUE + ", " +
                COLUMN_VALUE_ENTITY_ID + ", " +
                COLUMN_VALUE_ATRIBUTE_ID +
                ") VALUES (?, ?, ?, ?)";
        for (Value item : valuesArr) {
            int id = getKey();
            Object[] args = new Object[]{
                    id,
                    item.getValue(),
                    idEntity,
                    item.getAtributeId()
            };
            try {
            getJdbcTemplate().update(sql, args);
            } catch (DataAccessException e){
                logger.error("Can't addValue() "+e.getMessage());
                throw new DaoException("Data access Exception", e);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int add(Entity entity) {
        final String sql = "INSERT INTO TBL_ENTITY (" +
                COLUMN_ENTITY_ID + ", " +
                COLUMN_ENTITY_NAME + ", " +
                COLUMN_ENTITY_ISACTIVE + ", " +
                COLUMN_ENTITY_TYPE_ID + ", " +
                COLUMN_ENTITY_USER_ID +
                ") VALUES (?, ?, ?, ?, ?)";
        int id = getKey();
        Object[] args = new Object[]{
                id,
                entity.getEntityName(),
                entity.getisActive(),
                entity.getEntityTypeId(),
                entity.getEntityUserId()
        };
        try {
        getJdbcTemplate().update(sql, args);
        } catch (DataAccessException e){
            logger.error("Can't add() " +e.getMessage());
            throw new DaoException("Data access Exception", e);
        }
        if(entity.getValueList()!=null) {
            addValue(entity.getValueList(), id);
        }
        return id;
    }


    @Override
    public Entity getById(int id) {
        Entity entity=null;
        String sql = "SELECT E.ENTITYID ,E.ENTITYNAME ,E.ISACTIVE ,E.ENTITYTYPEID,T.ENTITYTYPENAME ,E.USERID  " +
                "FROM TBL_ENTITY E" +
                "    INNER JOIN TBL_ENTITYTYPE T" +
                "    ON E.ENTITYTYPEID=T.ENTITYTYPEID" +
                "    WHERE (E.ENTITYID = ?)";

        try {
             entity = getJdbcTemplate().queryForObject(sql, new Object[]{id}, new EntityRowMapper());
        } catch (EmptyResultDataAccessException e){
            logger.error("Can't getById() "+e.getMessage());
            throw new DaoException("Can't find entity with id = " + id, e);
        } catch (DataAccessException e){
            logger.error(e.getMessage());
            throw new DaoException("Data access Exception", e);
        }

        sql = "SELECT A.ATRIBUTEID ,A.ATRIBUTENAME ,A.ATRIBUTETYPEID ,T.ATRIBUTETYPENAME,A.ISACTIVE ,A.ENTITYTYPEID ,A.ISREQUIRED , V.VALUEID ,V.VALUE ,V.ENTITYID " +
                " FROM TBL_ATRIBUTE A " +
                " INNER JOIN TBL_ATRIBUTETYPE T " +
                " ON A.ATRIBUTETYPEID=T.ATRIBUTETYPEID" +
                " INNER JOIN TBL_VALUE V" +
                " ON A.ATRIBUTEID=V.ATRIBUTEID" +
                " WHERE (V.ENTITYID = ?)" +
                " ORDER BY A.SORTORDER";
        try {
        entity.setAtributeValueMap(getJdbcTemplate().query(sql, new Object[]{id}, new AtributeValueRowMapper()));
        } catch (EmptyResultDataAccessException e){
            logger.error("Can't getById() "+e.getMessage());
            throw new DaoException("Can't find Value with EntityId = " + id, e);
        } catch (DataAccessException e){
            logger.error(e.getMessage());
            throw new DaoException("Data access Exception", e);
        }
        return entity;
    }

    public void updateEntity(int id, String entityName, int isActive, int userId) {
        final String sqlUpdateEntity = "UPDATE TBL_ENTITY SET ENTITYNAME = ?, ISACTIVE = ?, " +
                "USERID = ? WHERE ENTITYID = ?";
        Object[] args = new Object[]{
                entityName,
                isActive,
                userId,
                id
        };
        try {
        getJdbcTemplate().update(sqlUpdateEntity, args);
        } catch (DataAccessException e){
            logger.error("Can't updateEntity() " +e.getMessage());
            throw new DaoException("Data access Exception", e);
        }
    }

    public void updateValue(List<Value> valuesArr) {
        String sql = "UPDATE TBL_VALUE SET VALUE = ? WHERE VALUEID = ?";
        List<Object[]> args = new ArrayList<>(valuesArr.size());
        for (Value v : valuesArr) {
            args.add(new Object[]{v.getValue(), v.getId()});
        }
        try {
        getJdbcTemplate().batchUpdate(sql, args);
        } catch (DataAccessException e){
            logger.error("Can't updateValue() " +e.getMessage());
            throw new DaoException("Data access Exception", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void update(int id, String entityName, int isActive, int userId, List<Value> valuesArr) {
        //update entity table
        updateEntity(id, entityName, isActive, userId);
        //update value table
        if (valuesArr != null) {
            updateValue(valuesArr);
        }
    }

    @Override
    public void updateByEntity(Entity entity) {
        update(entity.getId(), entity.getEntityName(), entity.getisActive(),
                entity.getEntityUserId(), entity.getValueList());
    }

    @Override
    public List<Entity> getList(int typeId, String atributesId, String values, String operators, String atributesIdView,int pageNumber, int pageSize) {
        List<String> entiyIdList=null;
        try {
        getJdbcTemplate().setResultsMapCaseInsensitive(true);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(PARAM_IN_ENTITY_ENTITYTYPEID, typeId)
                .addValue(PARAM_IN_ENTITY_ATRIBUTES, atributesId)
                .addValue(PARAM_IN_ENTITY_VALUES, values)
                .addValue(PARAM_IN_ENTITY_OPERATORS, operators)
                .addValue(PARAM_IN_PAGE_NUMBER, pageNumber)
                .addValue(PARAM_IN_PAGE_SIZE, pageSize);


        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(PROCEDURE_ENTITY_GET_LIST)
                .returningResultSet(PARAM_OUT_ENTITY_LIST, new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String string;
                        string=(rs.getString(COLUMN_ENTITY_ID));
                        int rnum = rs.getInt("RNUM");
                        return string;
                    }
                });

        Map result = jdbcCall.execute(in);
         entiyIdList = new ArrayList<>((ArrayList) result.get(PARAM_OUT_ENTITY_LIST));

        } catch (EmptyResultDataAccessException e){
            logger.error("Can't getList()"+e.getMessage());
            throw new DaoException("Entity, Attribute or Value table is empty", e);
        } catch (DataAccessException e){
            logger.error(e.getMessage());
            throw new DaoException("Data access Exception", e);
        }
        String strEntityIdList="";
        for(String item : entiyIdList){
            strEntityIdList+=","+item;
        }
        strEntityIdList=strEntityIdList.substring(1);
        return getListWithAttributes(strEntityIdList,atributesIdView);
    }

    @Override
    public List<Entity> getList(int typeId, String atributesId, String values, String operators, String atributesIdView) {
        List<String> entiyIdList=null;
        try {
        getJdbcTemplate().setResultsMapCaseInsensitive(true);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(PARAM_IN_ENTITY_ENTITYTYPEID, typeId)
                .addValue(PARAM_IN_ENTITY_ATRIBUTES, atributesId)
                .addValue(PARAM_IN_ENTITY_VALUES, values)
                .addValue(PARAM_IN_ENTITY_OPERATORS, operators);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(PROCEDURE_ENTITY_GET_LIST_NO_PAGINATION)
                .returningResultSet(PARAM_OUT_ENTITY_LIST, new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String string;
                        string=(rs.getString(COLUMN_ENTITY_ID));
                        return string;
                    }
                });

        Map result = jdbcCall.execute(in);
         entiyIdList = new ArrayList<>((ArrayList) result.get(PARAM_OUT_ENTITY_LIST));
        } catch (EmptyResultDataAccessException e){
            logger.error("Can't getList()"+e.getMessage());
            throw new DaoException("Entity, Attribute or Value table is empty", e);
        } catch (DataAccessException e){
            logger.error(e.getMessage());
            throw new DaoException("Data access Exception", e);
        }

        String strEntityIdList="";
        for(String item : entiyIdList){
            strEntityIdList+=","+item;
        }
        strEntityIdList=strEntityIdList.substring(1);
        return getListWithAttributes(strEntityIdList,atributesIdView);
    }

    private List<Entity> getListWithAttributes (String entiyIdList, String atributesId){
        List<String> attributesList=new ArrayList<>();
        List<Entity> entiyList=null;
        String[] arr = atributesId.split(",");
        for ( String ss : arr) {
            attributesList.add(ss);
        }

        try {
        getJdbcTemplate().setResultsMapCaseInsensitive(true);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(PARAM_IN_ENTITY_ENTITYID, entiyIdList)
                .addValue(PARAM_IN_ENTITY_ATRIBUTESID, atributesId);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(PROCEDURE_ENTITY_GET_LIST_VALUES)
                .returningResultSet(PARAM_OUT_ENTITY_LIST,new EAVlistRowMapper(attributesList));

        Map result = jdbcCall.execute(in);
         entiyList= new ArrayList<>((ArrayList)result.get(PARAM_OUT_ENTITY_LIST));

        } catch (EmptyResultDataAccessException e){
            logger.error("Can't getListWithAttributes()"+e.getMessage());
            throw new DaoException("Attribute or Value table is empty", e);
        } catch (DataAccessException e){
            logger.error(e.getMessage());
            throw new DaoException("Data access Exception", e);
        }

        return entiyList;

    }


    @Override
    public List<Entity> getByUserAndType(Integer userID, Integer entityTypeID, String atributesIdView) {
        List<String> entiyIdList;
        String sql = "SELECT  E.ENTITYID " +
                " FROM TBL_ENTITY E INNER JOIN TBL_ENTITYTYPE T ON E.ENTITYTYPEID=" +
                "T.ENTITYTYPEID WHERE (E." + COLUMN_ENTITY_USER_ID
                + "=?) AND ((? IS NOT NULL AND E." + COLUMN_ENTITY_TYPE_ID + "=?) OR " +
                "(? IS NULL))";

        try {
            entiyIdList = getJdbcTemplate().queryForList(sql, new Object[]{userID, entityTypeID,
                    entityTypeID, entityTypeID}, String.class);
        } catch (EmptyResultDataAccessException e){
            logger.error("Can't getByUserAndType()"+e.getMessage());
            throw new DaoException("Entity table is empty", e);
        } catch (DataAccessException e){
            logger.error(e.getMessage());
            throw new DaoException("Data access Exception", e);
        }
        String strEntityIdList="";
        for(String item : entiyIdList){
            strEntityIdList+=","+item;
        }
        strEntityIdList=strEntityIdList.substring(1);
        return getListWithAttributes(strEntityIdList,atributesIdView);
    }

    @Override
    public int rowCounter(int typeId, String atributesId, String values, String operators) {
        Map out=null;
        try {
        getJdbcTemplate().setResultsMapCaseInsensitive(true);
        SqlParameterSource in = new MapSqlParameterSource()
                .addValue(PARAM_IN_ENTITY_ENTITYTYPEID, typeId)
                .addValue(PARAM_IN_ENTITY_ATRIBUTES, atributesId)
                .addValue(PARAM_IN_ENTITY_VALUES, values)
                .addValue(PARAM_IN_ENTITY_OPERATORS, operators);

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate())
                .withProcedureName(PROCEDURE_ROWS_COUNTER);
         out = jdbcCall.execute(in);
    } catch (EmptyResultDataAccessException e){
        logger.error("Can't getByUserAndType()"+e.getMessage());
        throw new DaoException("Entity table is empty", e);
    } catch (DataAccessException e){
        logger.error(e.getMessage());
        throw new DaoException("Data access Exception", e);
    }

    return Integer.parseInt(String.valueOf(out.get(PARAM_OUT_COUNT)));
    }

    @Override
    public void delete(int id) {
        //final String sqlDeleteValue = "DELETE FROM TBL_VALUE WHERE ENTITYID = ?";
        final String sqlDeleteEntity = "UPDATE TBL_ENTITY SET ISACTIVE = 0 WHERE ENTITYID = ?";
        Object[] args = new Object[]{
                id
        };
        try {
        getJdbcTemplate().update(sqlDeleteEntity, args);
        } catch (DataAccessException e){
            logger.error("Can't delete()"+e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
    }

    private int getKey() {
        int out=-100;
        final String sql = "SELECT SQ_MAIN.NEXTVAL from dual";
        try {
         out = getJdbcTemplate().queryForObject(sql, Integer.class);
        } catch (DataAccessException e){
            logger.error("Can't getKey()"+e.getMessage());
            throw new DaoException(e.getMessage(), e);
        }
        return out;
    }

//    public List<Atribute> getAtributes(){
//
//    }

}
