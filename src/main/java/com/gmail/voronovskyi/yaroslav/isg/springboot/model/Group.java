package com.gmail.voronovskyi.yaroslav.isg.springboot.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "sgroups")
@NoArgsConstructor
public class Group {

    @Id
    @Column(name = "sgroup_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "sgroup", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Student> studentsList;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "sgroups_courses",
            joinColumns = {
                    @JoinColumn(name = "sgroup_ref", referencedColumnName = "sgroup_id"),
                    @JoinColumn(name = "sgroup_name", referencedColumnName = "name")},
            inverseJoinColumns = {
                    @JoinColumn(name = "course_ref", referencedColumnName = "course_id"),
                    @JoinColumn(name = "course_name", referencedColumnName = "name")})
    private List<Course> coursesList;
}
