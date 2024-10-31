package ma.formations.msa.accountservice.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
}
