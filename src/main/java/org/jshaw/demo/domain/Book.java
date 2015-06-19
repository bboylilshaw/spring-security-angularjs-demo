package org.jshaw.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Jason on 6/12/15.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;

}
