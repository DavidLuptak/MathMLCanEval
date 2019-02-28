package cz.muni.fi.mir.mathmlcaneval.domain;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formula_collections")
public class FormulaCollection extends BaseEntity {

  private String name;
  private String note;
  private Boolean visibleToPublic;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creator")
  private User creator;

  @OneToMany
  @JoinTable(name = "formula_collections_formulas",
    joinColumns = @JoinColumn(name = "formula_collection", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "formula", referencedColumnName = "id"))
  private Set<Formula> formulas;
}
