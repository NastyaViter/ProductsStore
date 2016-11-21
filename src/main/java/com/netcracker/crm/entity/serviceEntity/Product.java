package com.netcracker.crm.entity.serviceEntity;


import com.netcracker.crm.entity.AbstractEntity;
import com.netcracker.crm.entity.Atribute;
import com.netcracker.crm.entity.Entity;
import com.netcracker.crm.entity.Value;
import javafx.util.Pair;

import java.util.List;

public class Product extends AbstractEntity {
    private String name;    //название телефона или планшета
    private boolean isActive;
    private String productType; //телефон или планшет
    private int userId;
    private int price;
    private String summary;
    private List<Pair<Atribute, Value>> atributeValueMap;  //атрибуты телефона


    public Product() {
    }

    Product(Entity entityProd, Entity entityPhone) {
        this(entityProd.getId(), entityPhone.getEntityName(), entityProd.getisActive() == 1 ? true : false,
                entityPhone.getEntityTypeName(), entityPhone.getEntityUserId(), entityPhone.getAtributeValueMap());
        for(int i = 0; i<entityProd.getAtributeValueMap().size(); i++){
            if(entityProd.getAtributeValueMap().get(i).getKey().getAtributeTypeName().equals("Price")){
                price=Integer.parseInt(entityProd.getAtributeValueMap().get(i).getValue().getValue());
            }
            if(entityProd.getAtributeValueMap().get(i).getKey().getAtributeTypeName().equals("Summary")){
                summary=entityProd.getAtributeValueMap().get(i).getValue().getValue();
            }
        }
    }
    //for upper constructor
    public Product(int id, String name, boolean isActive, String productType, int userId, List<Pair<Atribute, Value>> avMap) {
        super(id);
        this.name = name;
        this.isActive = isActive;
        this.productType = productType;
        this.userId = userId;
        this.atributeValueMap = avMap;
    }

    public Product(int id, String name, boolean isActive, String productType, int userId, int price, String summary) {
        super(id);
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.isActive = isActive;
        this.userId = userId;
        this.summary = summary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<Pair<Atribute, Value>> getAtributeValueMap() {
        return atributeValueMap;
    }

    public void setAtributeValueMap(List<Pair<Atribute, Value>> atributeValueMap) {
        this.atributeValueMap = atributeValueMap;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Product product = (Product) o;

        if (isActive() != product.isActive()) return false;
        if (getUserId() != product.getUserId()) return false;
        if (getPrice() != product.getPrice()) return false;
        if (!getName().equals(product.getName())) return false;
        if (!getProductType().equals(product.getProductType())) return false;
        if (summary != null ? !summary.equals(product.summary) : product.summary != null) return false;
        return !(getAtributeValueMap() != null ? !getAtributeValueMap().equals(product.getAtributeValueMap()) : product.getAtributeValueMap() != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (isActive() ? 1 : 0);
        result = 31 * result + getProductType().hashCode();
        result = 31 * result + getUserId();
        result = 31 * result + getPrice();
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (getAtributeValueMap() != null ? getAtributeValueMap().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price + '\'' +
                ", productType='" + productType + "}";
    }
}