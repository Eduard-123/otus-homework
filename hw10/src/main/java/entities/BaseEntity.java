package entities;

import javax.persistence.*;

@MappedSuperclass
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
