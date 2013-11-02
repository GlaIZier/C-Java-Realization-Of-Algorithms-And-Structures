// InsertSort.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include <iostream>
#include <ctime>
using namespace std;

const int SIZE = 10;

class InsertSort {
		//int a[SIZE];
		int* pA;
	public:
		InsertSort(int* pA) 
		{ 
			this->pA = new int[SIZE];
			for (int i = 0; i < SIZE; i++) 
			{
				*this->pA = *pA;
				pA++;
				this->pA++;
			}
			this->pA = this->pA - SIZE;
			pA -= SIZE;
		}
		~InsertSort() 
		{
			delete [] this->pA;
		}
		void sort(); 
		void show();
};

void InsertSort::show() 
	{
			for (int i = 0; i < SIZE; i++) 
			{
				cout << *(pA++) << " ";
			}

			pA -= SIZE;
	};

void InsertSort::sort() 
{
	int i = 0, j = 0, current = 0;
	int* pAI; 

	for (i = 1; i < SIZE; i++)
	{
		pAI = pA + i;
		current = *pAI;
		cout << " current: " << current << "\n";
		for( j = i - 1; (j >= 0) && (current < *(pAI - 1)); j--)
		{
			pAI--;
			*(pAI + 1) = *pAI;
		}
		*pAI = current;
		show();
	}

	
}

int _tmain(int argc, _TCHAR* argv[])
{
	int exit;


	int i = 0;
	int* pA;
	pA = new int[SIZE];

	srand(time(NULL));
	for (i = 0; i < SIZE; i++) 
	{
		*pA = rand() % 100 + 1;
		pA++;
	}
	pA -= SIZE;

	InsertSort arrayToSort(pA);
	arrayToSort.show();
	arrayToSort.sort();
	arrayToSort.show();

	delete [] pA;

	cin >> exit; 
	return 0;
}

