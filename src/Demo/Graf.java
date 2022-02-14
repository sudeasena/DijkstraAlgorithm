package Demo;

import java.util.LinkedList;
import java.util.Stack;

class Graf {
    private LinkedList<Dugum> dugumler = new LinkedList<>();
    private Dijkstra dijkstra = new Dijkstra();

    String dugumEkle(double x, double y, String name) {
        Dugum temp = new Dugum(x, y, name);
        dugumler.add(temp);
        return ("dugum eklendi");
    }

    Dugum dugumGetir(String kaynak) {
        for (Dugum i : dugumler) {
            if (i.name.equals(kaynak)) {
                return i;
            }
        }
        return null;
    }

    String kenarEkle(String kaynak, String hedef, double agirlik) {
        Dugum kaynakDugum = null, hedefDugum = null;
        for (Dugum i : dugumler) {
            if (i.name.equals(kaynak)) {
                kaynakDugum = i;
            }
            if (i.name.equals(hedef)) {
                hedefDugum = i;
            }
        }
        if (kaynakDugum == null)
            return ("Girilen kaynak düğüm bulunamadı");
        else if (hedefDugum == null)
            return ("Girilen hedef düğümm bulunamadı");
        else {
            dijkstra.dijkstraKenarEkle(kaynakDugum, hedefDugum, agirlik);
            return ("Kenar eklendi");
        }

    }

    LinkedList<Dugum> getDugumler() {
        return dugumler;
    }

    Dijkstra getDijkstra() {
        return dijkstra;
    }

    Stack<Dugum> DugumYolu(String kaynak, String hedef) {
        Dugum kaynakDugum = null, hedefDugum = null;
        for (Dugum i : dugumler) {
            if (i.name.equals(kaynak)) {
                kaynakDugum = i;
            }
            if (i.name.equals(hedef)) {
                hedefDugum = i;
            }


        }
        return dijkstra.animasyonYolu(kaynakDugum, hedefDugum);
    }
}
