package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "deleted", type = "boolean"))
public class IEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键id")
    protected Long id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "是否被删除 ")
    protected boolean deleted = false;
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    protected Timestamp createTime;
    @UpdateTimestamp
    @ApiModelProperty(value = "最近更新时间")
    @JsonIgnore
    protected Timestamp updateTime;

    @JsonIgnore
    @ApiModelProperty(value = "用于优化数据库")
    private String indexJson;
}
