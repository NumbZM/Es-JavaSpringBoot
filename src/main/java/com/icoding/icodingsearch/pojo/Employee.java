package com.icoding.icodingsearch.pojo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Objects;

/**
 * @Description:Book实体 加上了@Document注解之后，默认情况下这个实体中所有的属性都会被建立索引、并且分词
 */
@Data
@Document(indexName = "megacorp",type = "employee", shards = 5,replicas = 1, refreshInterval = "-1")
public class Employee {
    @Id
    private String id;
    //@Field
    private String num;
    //@Field
    private String xm;
    //@Field
    private String phone;
    //@Field
    private String email;
    //@Field
    private String photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}