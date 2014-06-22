// QuickSearchIElement.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include <iostream>
#include <ctime>
using namespace std;

const int SIZE = 10;

class QuickRandomSearchIElement
{
	int array[SIZE];

public:
	
	QuickRandomSearchIElement()
	{
		srand(time(NULL));
		for (int i = 0; i < SIZE; i++)
		{
			array[i] = rand() % 100 + 1;
		}			
		array[0] = 9;
		array[1] = 8;
		array[2] = 3;
		array[3] = 3;
		array[4] = 6;
		array[5] = 2;
		array[6] = 1;
		array[7] = 5;
		array[8] = 0;
		array[9] = 7;
	};

	~QuickRandomSearchIElement()
	{
			cout << "\nDestruction of QuickRandomSearchIElement Object";
	};

	void show() 
	{
		for (int i = 0; i < SIZE; i++)
		{
			cout << array[i] << " ";
		}
		cout << "\n";
	}

	void selectIElement(int i) 
	{
		if ((i < 0) || (i >= SIZE))
		{
			cout << "Inputted wrong ordinal value!";
		}
		else 
		{
			randomSelect(0, SIZE - 1, i + 1); // поправка на то, что массив с 0 начинается
		}
	}

private:
	void randomSelect(int p, int r, int i)
	{
			if (p == r) 
			{
				cout << "I value is: " << array[p];
				return;
			}
			int q = partition(p, r); // divide reatively q. 
			if (i <= q - p + 1) 
			{
				randomSelect(p, q, i); // i in [p, q]
			}
			else 
			{
				randomSelect(q + 1, r, i - (q - p + 1)); // i in [q + 1, r]  Ищем уже в диапазоне не ию, а и - количество в левом диапазоне
			}

	}

	int partition(int p, int r) 
	{
		// Introduce random element to reduce bad case, when O(n^2)
		srand(time(NULL));
		int i1 = rand() % (r - p) + p; // rand in [p, r]
		cout << "i = " << i1 << "\n";
		int temp1 = array[p];
		array[p] = array[i1];
		array[i1] = temp1;  
		show();

		//partition
		int i = p - 1;
		int j = r + 1;
		int x = array[p];
		while (true) 
		{
			do
			{
				i++;
			} while (array[i] < x); //  && (i < j));
			do
			{
				j--;
			} while (array[j] > x);  //  && (i < j));
			if (i < j) 
			{
			    int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}
			else 
			{
				cout << "Border element = " <<  x << "\n";
				show();	
				cout << "q = " << j << "\n";
				cout << "\n";
				return j;
			}
		}
	}
};


int _tmain(int argc, _TCHAR* argv[])
{
	int exit;

	QuickRandomSearchIElement* quickRandomSearchIElement;
	quickRandomSearchIElement = new QuickRandomSearchIElement();
	quickRandomSearchIElement->show();
	cout << "\nInput ordinal number: ";
	int ordinalNumber;
	cin >> ordinalNumber;
	quickRandomSearchIElement->selectIElement(ordinalNumber);

	delete quickRandomSearchIElement;
	cin >> exit;
	return 0;
}

