package com.fzz.data.dao;

import com.fzz.data.entity.Actor;
import com.fzz.data.entity.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * @ClassName MovieRepository
 * @Description
 * @Author fzz
 * @Date 2018/11/6
 **/
@Repository
public interface ActorRepository extends Neo4jRepository<Actor,Long> {


}
