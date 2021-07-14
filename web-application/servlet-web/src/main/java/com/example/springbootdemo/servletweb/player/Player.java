package com.example.springbootdemo.servletweb.player;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class Player {

    private String id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotNull
    @Min(1)
    @Max(Short.MAX_VALUE)
    private Short age;

    public Player() {}

    public Player(String name, Short age) {
        this.name = name;
        this.age = age;
    }

    public Player(String id, String name, Short age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(age, player.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", age=" + age +
            '}';
    }

}
