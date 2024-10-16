package histoire;
import personnages.Chef;
import personnages.Druide;
import personnages.Gaulois;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.VillageSansChefException;

public class SenarioCasDegrade {
	public static void main(String[] args) {
		
		Etal etal = new Etal();
//		etal.libererEtal();
	//	System.out.println("Fin du test");
		
    	Village village = new Village("le village des irréductibles", 10, 5);
//		Chef abraracourcix = new Chef("Abraracourcix", 10, village);

	//	Gaulois achteur = null;
//		System.out.println(etal.acheterProduit(-10, abraracourcix));
//		Gaulois vendeur = new Gaulois("Obélix",25);
//		etal.occuperEtal(vendeur, "fleur", 20);
//		System.out.println("Fin du test");
		
		try {
            System.out.println(village.afficherVillageois());
        } catch (VillageSansChefException e) {
            System.err.println( e.getMessage());
        }
	
}
}
