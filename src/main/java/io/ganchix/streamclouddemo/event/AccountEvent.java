package io.ganchix.streamclouddemo.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountEvent {

  private Long id;
  private Integer customerId;
  private int amount;

}
