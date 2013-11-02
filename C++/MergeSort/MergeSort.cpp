// MergeSort.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include <iostream>
#include <ctime>
using namespace std;

const int SIZE = 100000;

class MergeSort 
{
	int* pA;

public:
	
	MergeSort(int a[SIZE])
	{
		pA = new int[SIZE];
		for (int i = 0; i < SIZE; i++, pA++)
		{
			*(this->pA) = a[i]; 
		}
		pA -= SIZE;
	};
	
	~MergeSort()
	{
		delete [] pA;
		cout << "Destuctor of MergeSort";
	};

	void show() 
	{
		for (int i = 0; i < SIZE; i++)
		{
			cout << *(pA + i) << " ";
		}
		cout << "\n";
	};

	void sort()
	{
		mergeSort(0, SIZE - 1);
	};

	void mergeSort(int p, int r) 
	{
		if (p < r)
		{
			int q = (p + r) / 2;
			mergeSort(p, q);
			mergeSort(q + 1, r);
			merge(p, q, r);
		}
	};

	void merge(int p, int q, int r)
	{
		int tempArray[SIZE];
		initialTempArray(tempArray, p, r);
			
		int i = p, j = q + 1, k = p;
		while ((i <= q) && (j <= r))
		{
			if (tempArray[i] <= tempArray[j])
			{
				*(pA + k) = tempArray[i];
				i++;
			}
			else 
			{
				*(pA + k) = tempArray[j];
				j++;
			}
			k++;
		}
		if (i > q) 
		{
			for (int i1 = j; i1 <= r; i1++)
			{
				*(pA + k) = tempArray[i1];
				k++;
			}
		}
		else
		{
			for (int i1 = i; i1 <= q; i1++)
			{
				*(pA + k) = tempArray[i1];
				k++;
			}
		}
	};

private: void initialTempArray(int tempArray[SIZE], int p, int r)
		 {
			int i; 
			for (i = 0; i < SIZE; i++)
			{
				tempArray[i] = 0;
			}

			for (i = p; i <= r; i++) 
			{
				tempArray[i] = *(pA + i);
			}
		 };
};


int _tmain(int argc, _TCHAR* argv[])
{
	int exit;

	int a[SIZE];
	srand(time(NULL));
	for (int i = 0; i < SIZE; i++)
	{
		a[i] = rand() % 100 + 1;
	}

	/*MergeSort mergeSort(a);
	mergeSort.show();
	mergeSort.sort();
	mergeSort.show();*/

	MergeSort* mergeSort;
	mergeSort = new MergeSort(a);
	mergeSort->show();
	mergeSort->sort();
	mergeSort->show();

	cout << "\nFree memory *mergeSort \n";
	delete mergeSort;

	cin >> exit;
	return 0;
}

