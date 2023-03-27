import java.util.ArrayList;

/**
 * Classe contenant le solveur du problème des missionnaires et cannibales.
 */
public class MissionnairesCannibales {

  static ArrayList<Double> memFile = new ArrayList<>();
  static ArrayList<Double> memPile = new ArrayList<>();

  /**
   * Nombre de missionnaires.
   */
  int nbMissionnaires;

  /**
   * Nombre de cannibales.
   */
  int nbCannibales;

  /**
   * File contenant les états à explorer.
   */
  FileListeChainee stepsFile;

  /**
   * Pile contenant les états à explorer.
   */
  PileListeChainee stepsPile;

  /**
   * Constructeur de la classe MissionnairesCannibales.
   *
   * @param n Nombre de missionnaires et cannibales.
   */
  public MissionnairesCannibales(int n) {
    nbMissionnaires = n;
    nbCannibales = n;
    stepsFile = new FileListeChainee();
    stepsPile = new PileListeChainee();
  }

  /**
   * Solveur du problème des missionnaires et cannibales utilisant une file.
   *
   * @param showSteps Affiche les étapes de la résolution.
   */
  public void solveFile(boolean showSteps) {
    Etat etatFileInit = new Etat(nbMissionnaires, nbCannibales, 0, 0, 'G');
    stepsFile.enfiler(etatFileInit);

    Etat etat;
    while (!stepsFile.fileVide()) {
      memFile.add((double) (Runtime.getRuntime().totalMemory()
          - Runtime.getRuntime().freeMemory()));
      ArrayList<Etat> nextSteps = new ArrayList<>();
      int tailleBase = stepsFile.taille();
      for (int i = 0; i < tailleBase; i++) {
        etat = (Etat) stepsFile.defiler();
        if (etat == null) {
          return;
        } else if (etat.estFinal()) {
          affichageFinal(etat);
          return;
        } else {
          if (showSteps) {
            System.out.println(etat);
          }
          ArrayList<Etat> temp = etat.prochainsEtats();
          for (Etat etatFile : temp) {
            if (!etatFile.contient(nextSteps)) {
              nextSteps.add(etatFile);
            }
          }
        }
      }
      for (Etat e : nextSteps) {
        stepsFile.enfiler(e);
      }
    }
  }

  /**
   * Solveur du problème des missionnaires et cannibales utilisant une pile.
   *
   * @param showSteps Affiche les étapes de la résolution.
   */
  public void solvePile(boolean showSteps) {
    Etat etatPileInit = new Etat(nbMissionnaires, nbCannibales, 0, 0, 'G');
    stepsPile.empiler(etatPileInit);

    Etat etat;
    while (!stepsPile.pileVide()) {
      memPile.add((double) (Runtime.getRuntime().totalMemory()
          - Runtime.getRuntime().freeMemory()));
      ArrayList<Etat> nextSteps = new ArrayList<>();
      int tailleBase = stepsPile.taille();
      for (int i = 0; i < tailleBase; i++) {
        etat = (Etat) stepsPile.depiler();
        if (etat == null) {
          return;
        } else if (etat.estFinal()) {
          affichageFinal(etat);
          return;
        } else {
          if (showSteps) {
            System.out.println(etat);
          }
          ArrayList<Etat> temp = etat.prochainsEtats();
          for (Etat etatPile : temp) {
            if (!etatPile.contient(nextSteps)) {
              nextSteps.add(etatPile);
            }
          }
        }
      }
      for (Etat e : nextSteps) {
        stepsPile.empiler(e);
      }
    }
  }

  /**
   * Affiche la solution trouvée et les états précédents.
   *
   * @param etat Etat final.
   */
  private void affichageFinal(Etat etat) {
    System.out.println("Solution trouvée!");
    System.out.println("Etat final:\n" + etat);
    Etat etatPrecedent = etat.etatPrecedent;
    while (etatPrecedent != null) {
      System.out.println(etatPrecedent);
      etatPrecedent = etatPrecedent.etatPrecedent;
    }
  }

  /**
   * Méthode permettant de mesurer l'impact sur la mémoire et le temps d'exécution des solveurs
   * via l'exécution des solveurs plusieurs fois.
   *
   * @param nbMissionnaires nombre de missionnaires et de cannibales.
   */
  private static void experiences(int nbMissionnaires) {
    ArrayList<Double> timesFile = new ArrayList<>();
    ArrayList<Double> timesPile = new ArrayList<>();
    ArrayList<Double> memFileMoy = new ArrayList<>();
    ArrayList<Double> memPileMoy = new ArrayList<>();
    ArrayList<Double> memFileMax = new ArrayList<>();
    ArrayList<Double> memPileMax = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      MissionnairesCannibales mc = new MissionnairesCannibales(nbMissionnaires);
      long start = System.nanoTime();
      mc.solveFile(false);
      timesFile.add((System.nanoTime() - start) * 1.0 / 1000000);

      start = System.nanoTime();
      mc.solvePile(false);
      timesPile.add((System.nanoTime() - start) * 1.0 / 1000000);

      memFileMoy.add((double)
          (Math.round(memFile.stream()
                .mapToDouble(Double::doubleValue)
                .average().orElse(0)) / 1000));
      memPileMoy.add((double)
          (Math.round(memPile.stream()
              .mapToDouble(Double::doubleValue)
              .average().orElse(0)) / 1000));
      memFileMax.add((double)
          (Math.round(memFile.stream()
              .mapToDouble(Double::doubleValue)
              .max().orElse(0)) / 1000));
      memPileMax.add((double)
          (Math.round(memPile.stream()
              .mapToDouble(Double::doubleValue)
              .max().orElse(0)) / 1000));

      memFile.clear();
      memPile.clear();
    }

    System.out.println("Temps d'exécution moyen pour une file : "
        + timesFile.stream().mapToDouble(Double::doubleValue).average().orElse(0) + "ms");
    System.out.println("Utilisation moyenne de la mémoire pour une file : "
        + memFileMoy.stream().mapToDouble(Double::doubleValue).average().orElse(0) + "ko");
    System.out.println("Utilisation maximale de la mémoire pour une file : "
        + memFileMax.stream().mapToDouble(Double::doubleValue).max().orElse(0) + "ko");

    System.out.println("Temps d'exécution moyen pour une pile : "
        + timesPile.stream().mapToDouble(Double::doubleValue).average().orElse(0) + "ms");
    System.out.println("Utilisation moyenne de la mémoire pour une pile : "
        + memPileMoy.stream().mapToDouble(Double::doubleValue).average().orElse(0) + "ko");
    System.out.println("Utilisation maximale de la mémoire pour une pile : "
        + memPileMax.stream().mapToDouble(Double::doubleValue).max().orElse(0) + "ko");
  }

  /**
   * Méthode permettant d'exécuter l'expérience une seule fois.
   *
   * @param nbMissionnaires nombre de missionnaires et de cannibales.
   */
  private static void experienceBase(int nbMissionnaires) {
    MissionnairesCannibales mc = new MissionnairesCannibales(nbMissionnaires);
    long start = System.nanoTime();
    mc.solveFile(false);
    double time1 = (System.nanoTime() - start) * 1.0 / 1000000;

    start = System.nanoTime();
    mc.solvePile(false);
    double time2 = (System.nanoTime() - start) * 1.0 / 1000000;

    System.out.println("Temps d'exécution avec une file : " + time1 + "ms");
    System.out.println("Mémoire moyenne utilisée avec une file : "
        + Math.round(memFile.stream()
        .mapToDouble(Double::doubleValue).average().orElse(0)) / 1000 + " Ko");
    System.out.println("Mémoirée maximum utilisée avec une file : "
        + Math.round(memFile.stream()
        .mapToDouble(Double::doubleValue).max().orElse(0)) / 1000 + " Ko");

    System.out.println("Temps d'exécution avec une pile : " + time2 + "ms");
    System.out.println("Mémoire moyenne utilisée avec une pile : "
        + Math.round(memPile.stream()
        .mapToDouble(Double::doubleValue).average().orElse(0)) / 1000 + " Ko");
    System.out.println("Mémoirée maximum utilisée avec une pile : "
        + Math.round(memPile.stream()
        .mapToDouble(Double::doubleValue).max().orElse(0)) / 1000 + " Ko");
  }

  /**
   * Méthode principale.
   *
   * @param args Arguments de la ligne de commande.
   */
  public static void main(String[] args) {
    experienceBase(6);
  }
}
