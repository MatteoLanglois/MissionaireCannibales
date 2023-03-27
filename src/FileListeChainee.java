/**
 * Implémentation d'une file par une liste chaînée.
 */
public class FileListeChainee implements File {
  class Maillon {
    Object element;
    Maillon suivant;

    Maillon(Object e) {
      element = e;
    }

    public String toString() {
      return element.toString();
    }
  }

  Maillon debut;
  Maillon fin;
  int nbElements;

  @Override
  public void enfiler(Object ob) {
    if (fileVide()) {
      debut = new Maillon(ob);
      fin = debut;
    } else {
      Maillon newM = new Maillon(ob);
      fin.suivant = newM;
      fin = newM;
    }
  }

  @Override
  public Object defiler() {
    if (fileVide()) {
      return null;
    } else {
      Object element = debut.element;
      debut = debut.suivant;
      return element;
    }
  }

  @Override
  public Object lireDebut() {
    return debut.element;
  }

  @Override
  public boolean fileVide() {
    return debut == null;
  }

  @Override
  public int taille() {
    int nb = 0;
    Maillon m = debut;
    while (m != null) {
      nb++;
      m = m.suivant;
    }
    return nb;
  }

  @Override
  public void afficherFile() {
    Maillon m = debut;
    System.out.print("File Liste Chainee F: ");
    while (m != null) {
      System.out.print(m.element + " ");
      m = m.suivant;
    }
    System.out.println();
  }

  @Override
  public String toString() {
    String s = "";
    Maillon m = debut;
    while (m != null) {
      s += m.element + " ";
      m = m.suivant;
    }
    return s;
  }

}