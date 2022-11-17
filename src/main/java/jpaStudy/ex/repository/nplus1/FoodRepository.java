package jpaStudy.ex.repository.nplus1;

import jpaStudy.ex.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
