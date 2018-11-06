package com.fzz.data.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Movie
 * @Description
 * @Author fzz
 * @Date 2018/11/2
 **/

@JsonIdentityInfo(generator = JSOGGenerator.class)
@NodeEntity
public class Movie {

    @GraphId
    private Long id;

    private String name;

    private String photo;

    @DateLong
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:dd")
    private Date createDate;

    @Relationship(type = "扮演", direction = Relationship.INCOMING)
    private List<Role> roles = new ArrayList();

    public Movie() {
    }

    public Role addRole(Actor actor, String name){
        Role role = new Role(actor,this,name);
        this.roles.add(role);
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
