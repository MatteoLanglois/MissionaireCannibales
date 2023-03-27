# Compte rendu pour le défi Missionnaires - Cannibales

## Conclusion

On peut remarquer que l'usage mémoire des files et des piles sont très équivalents
dans ce cas. On peut donc utiliser l'une ou l'autre sans se soucier de la consommation.

Cependant, les temps d'exécution sont plus intéressants. On remarque que la file est 
plus lente que la pile. Mais dans certains cas, la file est plus rapide que la pile.
Par exemple, pour 3 missionnaires et 3 cannibales, la pile prend 15ms tandis que la file
prend 48ms. Alors que pour 6 missionnaires et 6 cannibales, la file prend un peu moins de 6s
tandis que la pile prend 6.5s. 

On peut donc conclure que la file est plus lente que la pile pour les plus petites
valeurs de cannibales et missionnaires, mais que pour les plus grandes valeurs, la file
est plus rapide que la pile.