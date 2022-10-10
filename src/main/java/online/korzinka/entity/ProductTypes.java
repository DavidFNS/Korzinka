package online.korzinka.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product_types")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "findAll", query = "select pt from ProductTypes pt"),
        @NamedQuery(name = "findById", query = "select pt.id, pt.name, pt.unitId from ProductTypes pt")
})
public class ProductTypes {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "springWithSQL", strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    @Fetch(FetchMode.SUBSELECT)
    private List<Product> products;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "unit_id")
    private Integer unitId;
}
