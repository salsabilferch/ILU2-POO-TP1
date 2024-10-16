package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;        //Instance de la classe interne

	public Village(String nom, int nbVillageoisMaximum,int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}
	
	public static class Marche {              // Classe interne
		private Etal[] etals;
		
		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for(int i=0 ; i<nbEtals ; i++) {
				this.etals[i] = new Etal() ;
			}
		}
		
		private void utiliserEtal(int indiceEtal,Gaulois vendeur,String produit,int nbProduit) {
			if(indiceEtal < 0 && indiceEtal > etals.length) {
				return;
			}
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		private int trouverEtalLibre() {
			for(int i=0; i<etals.length; i++) {
				if(! etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		private Etal[] trouverEtals(String produit) {
			int nbProduit=0;
			for(int i=0; i<etals.length ; i++) {
				if(etals[i].contientProduit(produit) && etals[i].isEtalOccupe()) {
					nbProduit++;
				}
			}
			Etal[] etalsProduit = new Etal[nbProduit];      //Pour ne pas remplir etalsProduit avec des valeurs null
			int iProduit = 0;
			for(int i=0 ; i<etals.length ; i++ ) {
				if(etals[i].contientProduit(produit) && etals[i].isEtalOccupe()) {
					etalsProduit[iProduit]= etals[i];
					iProduit++;
				}
			}
			return etalsProduit;	
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0; i<etals.length; i++) {
				if(etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			
			int nbEtalVide = 0;
			StringBuilder chaine = new StringBuilder();
			
			for(int i=0; i<etals.length ; i++) {
				if(etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}
				else {
					nbEtalVide++;
				}
			}
			if(nbEtalVide != 0) {
				chaine.append("Il reste "+ nbEtalVide + " étals non utilisés dans le marché.\n");
			}
			return chaine.toString();

		}
	}
	
	public String installerVendeur(Gaulois vendeur,String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		String nomVendeur = vendeur.getNom();
		int numEtal = marche.trouverEtalLibre();
		
		chaine.append(nomVendeur +" cherche un endroit pour vendre " + nbProduit +" "+ produit + ".\n");
		
		if(numEtal != -1) {
			marche.utiliserEtal(numEtal, vendeur, produit, nbProduit);
		}
		chaine.append("Le vendeur " + nomVendeur + " vend des " + produit + " à l'étal n°" + numEtal + ".\n");
		return chaine.toString();
		
	}
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etals  = marche.trouverEtals(produit);
		
		if(etals.length == 0) {
			chaine.append("Il n'y pas de vendeur qui propose des " + produit + " au marché.\n");
		}
		else if(etals.length == 1 && etals[0] != null) {
			chaine.append("Seul le vendeur " + etals[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		}
		else {
			chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
			for(int i=0 ; i<etals.length ; i++) {
				if(etals[i] != null) {
					chaine.append("- " + etals[i].getVendeur().getNom()+"\n");
				}
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		 return marche.trouverVendeur(vendeur);
	}
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etal = rechercherEtal(vendeur);
		chaine.append(etal.libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals : \n");
		chaine.append(marche.afficherMarche());
		return chaine.toString();
	}
	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef!!!\n");
		}
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}
