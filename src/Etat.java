import java.util.ArrayList;

/**
 * Classe représentant un état du problème des missionnaires et cannibales.
 */
public class Etat {
  /**
   * ArrayList représentant la rive gauche du fleuve.
   */
  ArrayList<Character> riveGauche = new ArrayList<>();

  /**
   * ArrayList représentant la rive droite du fleuve.
   */
  ArrayList<Character> riveDroite = new ArrayList<>();

  /**
   * Position du bateau.
   */
  char posBateau = 'G';

  /**
   * Nombre total de missionnaires et cannibales.
   */
  int nbTotal = 0;

  /**
   * Etat précédent.
   *
   * @see Etat
   */
  Etat etatPrecedent = null;

  /**
   * Constructeur de la classe Etat.
   *
   * @param nbMissioGauche Nombre de missionnaires sur la rive gauche.
   * @param nbCanniGauche Nombre de cannibales sur la rive gauche.
   * @param nbMissioDroite Nombre de missionnaires sur la rive droite.
   * @param nbCanniDroite Nombre de cannibales sur la rive droite.
   * @param posBat Position du bateau.
   */

  public Etat(int nbMissioGauche, int nbCanniGauche,
              int nbMissioDroite, int nbCanniDroite, char posBat) {
    for (int i = 0; i < nbMissioGauche; i++) {
      riveGauche.add('M');
    }
    for (int i = nbMissioGauche; i < nbMissioGauche + nbCanniGauche; i++) {
      riveGauche.add('C');
    }

    for (int i = 0; i < nbMissioDroite; i++) {
      riveDroite.add('M');
    }
    for (int i = nbMissioDroite; i < nbMissioDroite + nbCanniDroite; i++) {
      riveDroite.add('C');
    }

    posBateau = posBat;
    nbTotal = nbMissioGauche + nbCanniGauche + nbMissioDroite + nbCanniDroite;
  }

  /**
   * Constructeur de la classe Etat.
   *
   * @param etat Etat à copier.
   */
  public Etat(Etat etat) {
    riveGauche = new ArrayList<>(etat.riveGauche);
    riveDroite = new ArrayList<>(etat.riveDroite);
    posBateau = etat.posBateau;
    nbTotal = etat.nbTotal;
    etatPrecedent = etat;
  }

  /**
  * Retourne une liste contenant les nombres de
  * missionnaires et cannibales sur chaque rive.
  *
  * @return Liste contenant les nombres de missionnaires et cannibales sur chaque rive.
  */
  public int[] compteurPersonnes() {
    int nbMissionnairesGauche = 0;
    int nbCannibalesGauche = 0;
    int nbMissionnairesDroite = 0;
    int nbCannibalesDroite = 0;

    for (char c : this.riveGauche) {
      if (c == 'M') {
        nbMissionnairesGauche++;
      } else if (c == 'C') {
        nbCannibalesGauche++;
      }
    }

    for (char c : this.riveDroite) {
      if (c == 'M') {
        nbMissionnairesDroite++;
      } else if (c == 'C') {
        nbCannibalesDroite++;
      }
    }
    return new int[] {nbMissionnairesGauche, nbCannibalesGauche,
        nbMissionnairesDroite, nbCannibalesDroite};
  }

  /**
   * Vérifie si l'état est final.
   *
   * @return Vrai si l'état est final, faux sinon.
   */
  public boolean estFinal() {
    int[] compteurs = this.compteurPersonnes();

    return compteurs[0] + compteurs[1] == 0
        && compteurs[2] + compteurs[3] == nbTotal;
  }

  /**
   * Vérifie si l'état est correct.
   * (Pas plus de cannibales que de missionnaires sur une rive,
   * ni plus de cannibales que de missionnaires sur l'autre rive,
   * ni plus de cannibales ou de missionnaires que le nombre de départ,
   * ni un état identique à un état précédent.)
   *
   * @return Vrai si l'état est correct, faux sinon.
   * @see Etat#verifiePrecedent()
   */
  public boolean etatCorrect() {
    int[] compteurs = this.compteurPersonnes();

    return (compteurs[0] >= compteurs[1] || compteurs[0] == 0)
        && (compteurs[2] >= compteurs[3] || compteurs[2] == 0)
        && (compteurs[0] + compteurs[2] == compteurs[1] + compteurs[3])
        && (compteurs[2] + compteurs[0] + compteurs[3] + compteurs[1]
        != compteurs[0] + compteurs[1]) && this.verifiePrecedent();
  }

  /**
   * Enlève d'une Arraylist les états identiques à l'état actuel.
   *
   * @param etats ArrayList d'états.
   * @return ArrayList d'états sans états identiques.
   */
  public static ArrayList<Etat> enleveDuplicats(ArrayList<Etat> etats) {
    ArrayList<Etat> etatsSansDuplicats = new ArrayList<>();
    for (Etat etat : etats) {
      if (!etatsSansDuplicats.contains(etat)) {
        etatsSansDuplicats.add(etat);
      }
    }
    return etatsSansDuplicats;
  }

  /**
   * Fonction qui réalise un déplacement du bateau.
   *
   * @param etat Etat actuel.
   * @param nbMissionnaires Nombre de missionnaires à déplacer.
   * @param nbCannibales Nombre de cannibales à déplacer.
   * @return Etat après le déplacement.
   */
  protected static Etat deplacePersonne(Etat etat, int nbMissionnaires, int nbCannibales) {
    Etat candidat = new Etat(etat);
    int[] count = candidat.compteurPersonnes();
    if (etat.posBateau == 'G') {
      if (nbMissionnaires > count[0] || nbCannibales > count[1]) {
        return null;
      }
      for (int i = 0; i < nbMissionnaires; i++) {
        candidat.riveGauche.remove(Character.valueOf('M'));
        candidat.riveDroite.add('M');
      }
      for (int i = 0; i < nbCannibales; i++) {
        candidat.riveGauche.remove(Character.valueOf('C'));
        candidat.riveDroite.add('C');
      }
      candidat.posBateau = 'D';
    } else {
      if (nbMissionnaires > count[2] || nbCannibales > count[3]) {
        return null;
      }
      for (int i = 0; i < nbMissionnaires; i++) {
        candidat.riveDroite.remove(Character.valueOf('M'));
        candidat.riveGauche.add('M');
      }
      for (int i = 0; i < nbCannibales; i++) {
        candidat.riveDroite.remove(Character.valueOf('C'));
        candidat.riveGauche.add('C');
      }
      candidat.posBateau = 'G';
    }
    if (candidat.etatCorrect()) {
      return candidat;
    }
    return null;
  }

  /**
   * Fonction permettant d'afficher l'état.
   *
   * @return String représentant l'état.
   */
  public String toString() {
    int nbMax = Math.max(riveGauche.size(), riveDroite.size());
    String str = "";
    for (int i = 0; i < nbMax; i++) {
      if (i < riveGauche.size()) {
        str += riveGauche.get(i);
      } else {
        str += " ";
      }
      if (i == nbMax / 2) {
        if (posBateau == 'G') {
          str += " B\t\t\t";
        } else {
          str += "\t\t\tB ";
        }
      } else {
        str += "\t\t\t\t";
      }
      if (i < riveDroite.size()) {
        str += riveDroite.get(i);
      } else {
        str += " ";
      }
      str += "\n";
    }
    return str.toString();
  }

  /**
   * Fonction permettant de comparer deux états.
   *
   * @param o Objet à comparer.
   * @return Vrai si les deux états sont identiques, faux sinon.
   */
  public boolean equals(Object o) {
    if (o instanceof Etat) {
      Etat etat = (Etat) o;
      return riveGauche.equals(etat.riveGauche)
          && riveDroite.equals(etat.riveDroite)
          && posBateau == etat.posBateau;
    }
    return false;
  }

  /**
   * Fonction permettant de vérifier si l'état est déjà présent dans une liste.
   *
   * @param nextSteps Liste d'états.
   * @return Vrai si l'état est déjà présent, faux sinon.
   */
  protected boolean contient(ArrayList<Etat> nextSteps) {
    for (Etat etatPile : nextSteps) {
      if (etatPile.riveGauche.equals(this.riveGauche)
          && etatPile.riveDroite.equals(this.riveDroite)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Fonction permettant de vérifier si l'état actuel n'est pas déjà présent dans
   * la liste des états précédents.
   *
   * @return Vrai si l'état n'est pas déjà présent, faux sinon.
   */
  protected boolean verifiePrecedent() {
    Etat etatPrecedent = this.etatPrecedent;
    while (etatPrecedent != null) {
      if (etatPrecedent.equals(this)) {
        return false;
      }
      etatPrecedent = etatPrecedent.etatPrecedent;
    }
    return true;
  }

  /**
   * Fonction permettant d'obtenir la liste des états suivants.
   *
   * @return Liste des états suivants.
   */
  public ArrayList<Etat> prochainsEtats() {
    ArrayList<Etat> nextSteps = new ArrayList<>();
    Etat candidat = deplacePersonne(this, 0, 1);
    if (candidat != null) {
      nextSteps.add(candidat);
    }
    candidat = deplacePersonne(this, 0, 2);
    if (candidat != null) {
      nextSteps.add(candidat);
    }
    candidat = deplacePersonne(this, 1, 0);
    if (candidat != null) {
      nextSteps.add(candidat);
    }
    candidat = deplacePersonne(this, 2, 0);
    if (candidat != null) {
      nextSteps.add(candidat);
    }
    candidat = deplacePersonne(this, 1, 1);
    if (candidat != null) {
      nextSteps.add(candidat);
    }
    return enleveDuplicats(nextSteps);
  }

}
