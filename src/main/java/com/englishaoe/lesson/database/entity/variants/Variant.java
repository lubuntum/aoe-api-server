package com.englishaoe.lesson.database.entity.variants;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "variant")

public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "theme")
    private String theme;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "creation_date")
    private String creationDate;
    @Column(name = "is_visible")
    private Boolean isVisible;

    @OneToMany(mappedBy = "variantId", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Task> variantTasks;
}
