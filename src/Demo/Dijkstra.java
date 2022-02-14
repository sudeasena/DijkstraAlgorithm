package Demo;

import java.util.*;

public class Dijkstra {
    private Set<Dugum> dugumler;

    Dijkstra(){
        dugumler = new HashSet<>();
    }

    void dijkstraKenarEkle(Dugum kaynak,Dugum hedef,double agirlik){
        dugumler.add(kaynak);
        dugumler.add(hedef);



        if(kaynak != hedef){
            kaynak.kenarlar.add(new Kenar(kaynak,hedef,agirlik));
        }
    }

    //Düğümler listesindeki tüm kenarları kenarlar listesine kopyalıyor.
    void dugumKenarEkleme(ArrayList<Kenar> kenarlar){
        for(Dugum i:dugumler){
            kenarlar.addAll(i.kenarlar);
        }
    }

    Stack<Dugum> animasyonYolu(Dugum kaynak,Dugum hedef){
        Stack<Dugum> yol = new Stack<>();
        HashMap<Dugum,Dugum> yolParcacigi = new HashMap<>();
        yolParcacigi.put(kaynak,null);

        HashMap<Dugum,Double> enKisaYolHaritasi = new HashMap<>();
        //Baslangıç düğümüne 0 diğer düğümlere sonsuz değer ataması yapılır.
        for(Dugum i: dugumler){
            if(i==kaynak){
                enKisaYolHaritasi.put(kaynak,0.0);
            }
            else{
                enKisaYolHaritasi.put(i,Double.MAX_VALUE);
            }
        }
        //Kaynak düğümün komşularının ağırlıklarının güncelleniyor.
        for(Kenar i: kaynak.kenarlar){
            enKisaYolHaritasi.put(i.hedef,i.agirlik);
            yolParcacigi.put(i.hedef,kaynak);
        }
        kaynak.visit();

        while(true){
            Dugum gecerliDugum = enYakinZiyaretEdilmemisDugum(enKisaYolHaritasi);

            if(gecerliDugum==null){
                return null;
            }


            if(gecerliDugum==hedef){
                Dugum child = hedef;
                yol.push(child);
                while(true){
                    Dugum parent = yolParcacigi.get(child);
                    if(parent==null){
                        break;
                    }
                    yol.push(parent);
                    child=parent;
                }
                return yol;
            }
            gecerliDugum.visit();

            for(Kenar i: gecerliDugum.kenarlar){
                if(i.hedef.isVisited()){
                    continue;
                }

                if(enKisaYolHaritasi.get(gecerliDugum)+i.agirlik<enKisaYolHaritasi.get(i.hedef)){
                    enKisaYolHaritasi.put(i.hedef,enKisaYolHaritasi.get(gecerliDugum)+i.agirlik);
                    yolParcacigi.put(i.hedef,gecerliDugum);
                }
            }
        }
    }

    private Dugum enYakinZiyaretEdilmemisDugum(HashMap<Dugum, Double> enKisaYolHaritasi){
        double enKisaMesafe=Double.MAX_VALUE;
        Dugum enYakinKomsu=null;
        for(Dugum i: dugumler){
            if(i.isVisited()){
                continue;
            }

            double gecerliMesafe = enKisaYolHaritasi.get(i);
            if(gecerliMesafe==Double.MAX_VALUE){
                continue;
            }

            if(gecerliMesafe<enKisaMesafe){
                enKisaMesafe=gecerliMesafe;
                enYakinKomsu=i;
            }
        }
        return enYakinKomsu;
    }




}
