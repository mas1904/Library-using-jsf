#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <list>
#include <conio.h>
using namespace std;
class CSVRow
{
    public:
        char                del;
        std::string const& operator[](std::size_t index) const
        {
            return m_data[index];
        }
        std::size_t size() const
        {
            return m_data.size();
        }
        void readNextRow(std::istream& str)
        {
            std::string         line;
            std::getline(str, line);

            std::stringstream   lineStream(line);
            std::string         cell;

            m_data.clear();
            while(std::getline(lineStream, cell, del))
            {
                m_data.push_back(cell);
            }
        }
    private:
        std::vector<std::string>    m_data;
};

std::istream& operator>>(std::istream& str, CSVRow& data)
{
    data.readNextRow(str);
    return str;
}

int main()
{
    setlocale( LC_ALL, "" );
    string sciezka;
    cout << "Podaj plik: ";
    cin >> sciezka;
    ifstream plik;
    plik.open(sciezka.c_str(),ios::in);
    if(plik.is_open() == true){
        cout << "Podaj selektor: ";
        CSVRow              row;
        cin >> row.del;
        cout << "Wczytana tablica" << endl;
        if(plik >> row)
            for(int i=0;i<row.size();i++){
                cout << i+1 << "." << row[i] << " ";
            }
        int kol = row.size();
        list<string> decyzyjne;
        bool byl=false;
        int elem = 0;
        while(plik >> row)
            elem++;
        string tablica[kol][elem];
        plik.clear();
        plik.seekg(0);
        plik >> row;
        cout << endl;
        int j=0;
        while(plik >> row)
        {
            byl=false;
            cout << j+1 << ". ";
            for(int i=0;i<row.size()-1;i++){
                cout << row[i] << " ";
                tablica[i][j]=row[i];
            }
            for(list<string>::iterator iter=decyzyjne.begin(); iter != decyzyjne.end(); ++iter)
                if(row[row.size()-1]==*iter)
                    byl=true;
            if(byl==false)
                decyzyjne.push_back(row[row.size()-1]);
            tablica[row.size()-1][j]=row[row.size()-1];
            cout << row[row.size()-1];
            cout << endl;
            j++;
        }
        decyzyjne.sort();
        cout << "Decyzje: " << endl;
        for(list<string>::iterator iter=decyzyjne.begin(); iter != decyzyjne.end(); ++iter)
            cout << "X_" << *iter << endl;
        cout << "\nPodaj atrybuty warunkowe na których\n ma zostaæ zdefiniowana relacja nierozró¿nialnoœci(0 - zatrzymaj): " << endl;
        int tab[kol];
        int stop = 0;
        int a=9;
        while(a!=0){
            cout << "Nr. atrybutu: ";
            cin >> a;
            tab[stop] = a-1;
            stop++;
        }
        cout << "------------------" << endl;
        stop--;
        int w = elem;
        bool bylo[w];
        for(int i=0;i<w;i++)
            bylo[i]=false;
        int odwolania=0;
        int index=0;
        bool inne = false;
        int il_dec = decyzyjne.size();
        list<string> dec;
        list<int> zb;
        list<int> gorna[il_dec];
        list<int> dolna[il_dec];
        list<int> brzeg[il_dec];

        while(odwolania!=w){
            if(bylo[index]==false){
                odwolania++;
                zb.clear();
                dec.clear();
                zb.push_back(index);
                dec.push_back(tablica[kol-1][index]);
                string bb = tablica[kol-1][index];
                for(int i=index+1;i<w;i++)
                    if(bylo[i]==false){
                        inne = false;
                        for(j=0;j<stop;j++){
                            if(tablica[tab[j]][index]!=tablica[tab[j]][i])
                                inne = true;
                        }
                        if(inne == false){
                            bylo[i]=true;
                            odwolania++;
                            zb.push_back(i);
                            for(list<string>::iterator iter=dec.begin(); iter != dec.end(); iter++)
                                if(*iter!=tablica[kol-1][i])
                                    dec.push_back(tablica[kol-1][i]);
                        }
                    }
                if(dec.size()==1){
                    list<string>::iterator iter2=dec.begin();
                    dec.sort();
                    int k=0;
                    for(list<string>::iterator iter=decyzyjne.begin(); iter != decyzyjne.end(); iter++){
                        if(*iter2==*iter){
                            for(list<int>::iterator it=zb.begin();it!=zb.end();it++){
                                gorna[k].push_back(*it);
                                dolna[k].push_back(*it);
                            }
                        }
                        k++;
                    }
                } else {
                    dec.sort();
                    list<string>::iterator iter2=dec.begin();
                    list<string>::iterator iter=decyzyjne.begin();
                    for(int k=0;k<il_dec;k++){
                        if(*iter==*iter2){
                            for(list<int>::iterator it=zb.begin();it!=zb.end();it++){
                                gorna[k].push_back(*it);
                                brzeg[k].push_back(*it);
                            }
                        }
                    }
                }
            }
            index++;
        }
        int i=0;
        for(list<string>::iterator iter=decyzyjne.begin(); iter != decyzyjne.end(); ++iter){
            gorna[i].sort();
            dolna[i].sort();
            brzeg[i].sort();
            cout << "Aproksymacja górna: ^BX_" << *iter << "={ ";
            for(list<int>::iterator iter2=gorna[i].begin(); iter2!=gorna[i].end(); iter2++)
                cout << *iter2+1 << ", ";
            cout << " }" << endl;
            cout << "Aproksymacja dolna: _BX_" << *iter << "={ ";
            for(list<int>::iterator iter2=dolna[i].begin(); iter2!=dolna[i].end(); iter2++)
                cout << *iter2+1 << ", ";
            cout << " }" << endl;
            cout << "Obszar brzegowy: BNB_(X" << *iter << ")={ ";
            for(list<int>::iterator iter2=brzeg[i].begin(); iter2!=brzeg[i].end(); iter2++)
                cout << *iter2+1 << ", ";
            cout << " }" << endl;
            cout << "===="<<endl;
            i++;
        }
    } else cout << "brak dostêpu do pliku";
    getch();
    plik.close();
    return 0;
}
