/**
 * Implémentation d'une pile avec une liste chaînée.
 */
public class PileListeChainee implements Pile {

  class Maillon {
    Object element; // element de donnee
    Maillon suivant; // prochain maillon dans la liste chainee

    Maillon(Object e) {
      element = e; // le prochain est automatiquement mis à null
    }

    public String toString() {
      return element.toString();
    }
  }

  Maillon sommet; // sommet de la pile
  Maillon maillonCourant; // maillon courant

  public PileListeChainee() {
    sommet = null;
  }

  @Override
  public void empiler(Object o) {
    maillonCourant = new Maillon(o);
    maillonCourant.suivant = sommet;
    sommet = maillonCourant;
  }

  @Override
  public Object depiler() {
    if (sommet == null) {
      return null;
    }
    Maillon old = sommet;
    sommet = sommet.suivant;
    maillonCourant = sommet;
    return old.element;
  }

  @Override
  public Object lireSommet() {
    return sommet;
  }

  @Override
  public boolean pileVide() {
    return sommet == null;
  }

  @Override
  public int taille() {
    int taille = 0;
    while (maillonCourant != null) {
      taille++;
      maillonCourant = maillonCourant.suivant;
    }
    return taille;
  }

  @Override
  public void afficherPile() {
    System.out.print("[ ");
    while (maillonCourant != null) {
      System.out.print(maillonCourant.element + " ");
      maillonCourant = maillonCourant.suivant;
    }
    System.out.println("]");
  }


}
