import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.io.GmlImporter;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.VertexProvider;

import org.jgrapht.alg.cycle.HierholzerEulerianCycle;

import java.util.ArrayList;
import java.util.Arrays;

public class ImportSimpleGraphGML {
	// Importa Grafo Simples no formato GML com r�tulo nos v�rtices e nas arestas

	public static void main(String[] args) {
		VertexProvider<Object> vp1 =
				DefaultVertex::new;

		EdgeProvider<Object, RelationshipEdge> ep1 =
				(from, to, label, attributes) -> new RelationshipEdge(from, to, attributes);

		GmlImporter<Object, RelationshipEdge> gmlImporter = new GmlImporter<>(vp1, ep1);

		Graph<Object, RelationshipEdge> graphgml = new SimpleGraph<>(RelationshipEdge.class);

		try {
			gmlImporter.importGraph(graphgml, ImportGraph.readFile("/home/joaop/TeoriaGraf/rede.gml"));
		} catch (ImportException e) {
			throw new RuntimeException(e);
		}

		System.out.println("\nGrafo importado do arquivo GML: ");

		HierholzerEulerianCycle<Object, RelationshipEdge> graph = new HierholzerEulerianCycle<>();

		if (graph.isEulerian(graphgml)) {

			//Conversao do caminho gerado para array e posteriormente para ArrayList, possibilitando modificaçao.
			Object[] path = graph.getEulerianCycle(graphgml).getVertexList().toArray();
			ArrayList<Object> list = new ArrayList<>(Arrays.asList(path));


			//Adiciona o passo no final e remove do inicio.
			for (int i = 1; i < list.size(); i++) {

				if (!path[i].toString().equals("C")) {
					list.add(path[i]);
					list.remove(path[i]);
				} else break;
			}

			//Retirando o primeiro, repetido.
			list.remove(0);

			StringBuilder saida = new StringBuilder();
			for (Object aList : list) {
				saida.append(aList.toString()).append(" >> ");
			}

			System.out.println(saida);

		} else {
			System.out.println("Não é possível encontrar uma rota neste grafo");
		}
	}

}

