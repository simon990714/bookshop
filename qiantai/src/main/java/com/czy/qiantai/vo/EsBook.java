package com.czy.qiantai.vo;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Document(indexName = "esbook",shards = 3,replicas = 2)
@Data
public class EsBook implements Serializable {

    @Id
    private Long id;

    @Field(name = "name",analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Keyword)
    private Integer typeId;

    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String typeName ;

    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String provider;

    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Double)
    private BigDecimal price;

    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_max_word",type = FieldType.Text)
    private String detail;

    @Field(type = FieldType.Keyword)
    private String imgsrc;

    @Field(type = FieldType.Integer)
    private Integer collectioncount;

    @Field(type = FieldType.Integer)
    private Integer storecount;

    @Field(type = FieldType.Integer)
    private Integer buycount;

    @Field(type = FieldType.Integer)
    private Integer readcount;

    @Field(type = FieldType.Date,format = DateFormat.date)
    private Date createtime;

    @Field(type = FieldType.Date,format = DateFormat.date)
    private Date updatetime;

    @Field(type = FieldType.Integer)
    private Integer state;

}


