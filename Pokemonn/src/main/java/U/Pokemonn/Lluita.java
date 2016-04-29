package U.Pokemonn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lluita {

	Connection conect = null;
	List<Pokemon> pokemon = new ArrayList();
	List<Pokemon> equipo = new ArrayList();
	List<Pokemon> rival = new ArrayList();

	public void huebo() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conect = DriverManager.getConnection("jdbc:mysql://192.168.4.1/pokemons", "pokemon", "pokemon");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String pokeConsulta = "SELECT p.nom, p.pes, pp.valor, pt.tipus_id"
				+ "FROM pokemon p, pokemon_poder pp, poketipus pt" + "WHERE p.pokemon_id = pt.pokemon_id and"
				+ "p.pokemon_id = pp.pokemon_id and"
				+ "pp.poder_id = (SELECT po.poder_id FROM poders po WHERE po.nom = 'Atac')";

		Statement pokePeticio;
		try {

			pokePeticio = conect.createStatement();
			ResultSet resultats = pokePeticio.executeQuery(pokeConsulta);

			while (resultats.next()) {
				String nom = resultats.getString("p.nom");
				int pes = resultats.getInt("p.pes");
				int valor = resultats.getInt("pp.valor");
				int tipus = resultats.getInt("pt.tipus_id");
				pokemon.add(new Pokemon(nom, pes, valor, tipus));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void hierbaAlta() {

		Random rand = new Random();

		equipo.add(pokemon.get(rand.nextInt(pokemon.size())));
		equipo.add(pokemon.get(rand.nextInt(pokemon.size())));

		rival.add(pokemon.get(rand.nextInt(pokemon.size())));
		rival.add(pokemon.get(rand.nextInt(pokemon.size())));

	}

	public void pokemonSalvajeAparacio() {
		Pokemon pokeEquipo = equipo.get(0);
		Pokemon pokeRival = rival.get(0);

		int pierdeEquipo = 0;
		int pierdeRival = 0;

		double multiplicaEquipo = efectotipos(pokeEquipo.getTipo(), pokeRival.getTipo());
		double multiplicaRival = efectotipos(pokeRival.getTipo(), pokeEquipo.getTipo());

		while (pierdeEquipo != 2 || pierdeRival != 2) {
			combate(pokeEquipo, pokeRival , multiplicaEquipo, multiplicaRival );

			if (pokeEquipo.getSalut() <= 0) {
				pokeEquipo = equipo.get(1);
				pierdeEquipo++;

			} else if (pokeRival.getSalut() <= 0) {
				pokeRival = rival.get(1);
				pierdeRival++;

			}

		}

	}

	private void combate(Pokemon pokeEquipo,Pokemon pokeRival, double multiplicaEquipo, double multiplicaRival) {
		if (pokeEquipo.velocidad() < pokeRival.velocidad()) {
			double ataque = pokeEquipo.golpeCuerpo() * multiplicaEquipo ;
			pokeRival.debilita(ataque);

			System.out.println(pokeEquipo.nombre() + " usó golpe cuerpo contra " + pokeRival.nombre() + ".");
			System.out.println(pokeRival.nombre() + " pierde "+ataque+" puntos de vida");

			ataque = pokeRival.golpeCuerpo() * multiplicaRival ;
			pokeEquipo.debilita(ataque);

			System.out.println(pokeRival.nombre() + " usó golpe cuerpo contra " + pokeEquipo.nombre() + ".");
			System.out.println(pokeEquipo.nombre() + " pierde "+ataque+" puntos de vida");
		} else {
			double ataque = pokeRival.golpeCuerpo() * multiplicaEquipo ;
			pokeEquipo.debilita(ataque);

			System.out.println(pokeEquipo.nombre() + " usó golpe cuerpo contra " + pokeRival.nombre() + ".");
			System.out.println(pokeRival.nombre() + " pierde "+ataque+" puntos de vida");

			ataque = pokeEquipo.golpeCuerpo() * multiplicaRival ;
			pokeRival.debilita(ataque);

			System.out.println(pokeRival.nombre() + " usó golpe cuerpo contra " + pokeEquipo.nombre() + ".");
			System.out.println(pokeEquipo.nombre() + " pierde "+ataque+" puntos de vida");
		}

	}

	private double efectotipos(int tipoEquipo, int tipoRival) {
		String pokeConsultaTipos = "SELECT t.efecte FROM tipus_atac t WHERE t.tipus_atacant_id ="+tipoEquipo+" and"
								 + "t.tipus_atacat_id ="+tipoRival;

		Statement pokePeticionTipos;
		double efecto = 0;
		try {

			pokePeticionTipos = conect.createStatement();
			ResultSet resultats = pokePeticionTipos.executeQuery(pokeConsultaTipos);
			
			efecto = resultats.getDouble("t.efecte");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return efecto;
	}

}
