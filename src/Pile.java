/**
 * Interface Pile.
 */
public interface Pile {
  public void empiler(Object o);

  public Object depiler();

  public Object lireSommet();

  public boolean pileVide();

  public int taille();

  public void afficherPile();
}
