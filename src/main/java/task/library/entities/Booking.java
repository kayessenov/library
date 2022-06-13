package task.library.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking extends BaseEntity{
    @Column(name = "amount")
    private int amount;

    @ManyToOne
    private Users users;

    @ManyToOne
    private Books books;
}
