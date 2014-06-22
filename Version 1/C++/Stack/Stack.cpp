// Stack.cpp: определяет точку входа для консольного приложения.
//

#include "stdafx.h"
#include <iostream>
#include <ctime>
using namespace std;


class Element
{
	Element* next;

	Element* prev;

	int key;

public:

	Element(int key)
	{
		this->key = key;
	}

	int getKey()
	{
		return key;
	}

	Element* getNext() 
	{
		return next;
	}

	Element* getPrev() 
	{
		return prev;
	}

	void setKey(int key)
	{
		this->key = key;
	}

	void setNext(Element* next)
	{
		this->next = next;
	}

	void setPrev(Element* prev)
	{
		this->prev = prev;
	}
};

class Stack  
{
	Element* top;

public:
	Stack(int firstElementKey) 
	{
		top = new Element(firstElementKey);
		top->setNext(NULL);
		top->setPrev(NULL);
	}

	~Stack() 
	{
		if (top != NULL) 
		{
			Element* current = top;
			while (current->getNext() != NULL)
			{
				current = current->getNext();
				current->setPrev(NULL);
				top->setNext(NULL);
				delete top;
				top = current;
			}
			delete current;
			top = NULL;
			current = NULL;
		}
		cout << "Stack destrutor \n";
	}

	void push(int key) 
	{
		Element* newTop = new Element(key);
		newTop->setPrev(NULL);
		newTop->setNext(top);
		if (top != NULL)
		{
			top->setPrev(newTop);
		}
		top = newTop;
	}

	void pop()
	{
		if (top != NULL) 
		{
			cout  << top->getKey() << " ";
		}
		else 
		{
			cout << "Stack is empty! Nothing to pop\n";
			return;
		}
		if (top->getNext() == NULL) 
		{
			top->setNext(NULL);
			top->setPrev(NULL);
			delete top;
			top = NULL;
		}
		else
		{
			top = top->getNext();
			top->getPrev()->setNext(NULL);
			top->getPrev()->setPrev(NULL);
			delete top->getPrev();
			top->setPrev(NULL);
		}
	}

	void show() 
	{
		Element* current = top;
		if (current == NULL) 
		{
			cout << "Stack is empty! Nothing to show\n";
			return;
		}
		while (current->getNext() != NULL) 
		{
			cout << current->getKey() << " ";
			current = current->getNext();
		}
		cout << current->getKey() << "\n";
		current = NULL;
	}
};



int _tmain(int argc, _TCHAR* argv[])
{
	int exit;

	Stack* stack;
	stack = new Stack(1);
	stack->push(3);
	stack->push(4);
	stack->push(5);
	stack->pop();
	stack->pop();
	stack->pop();
	stack->pop();
	cout << "\n";
	stack->show();
	stack->pop();
	stack->push(1010);
	stack->push(1011);
	stack->push(1100);
	stack->show();
	
	delete stack;
	cin >> exit;
	return 0;
}

