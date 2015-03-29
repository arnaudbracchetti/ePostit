/*
 * postit.cpp
 *
 * Created: 10/02/2015 21:04:38
 *  Author: Arnaud
 */ 

#define setb(port, bit) port |= _BV(bit)
#define clearb(port, bit) port &= ~_BV(bit)
#define readb(port, bit) port &= _BV(bit)



#define PORT_ACCESS(longName, port, pin) \
	inline void set_##longName##_out() { setb(DDR##port, DD##port##pin);} \
	inline void set_##longName##_in() { clearb(DDR##port, DD##port##pin);} \
	inline void get_##longName##_state() { readb(PIN##port, PIN##port##pin);} \	
	inline void set_##longName##_high() { setb(PORT##port, PORT##port##pin);} \
	inline void set_##longName##_low() { clearb(PORT##port, PORT##port##pin);} 			
		
		






//#define NEXT_BIT 24 


#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdlib.h>
#include <avr/delay.h>


// configuration 
#define MAX_COL 2				// nombre maximum de colonne dans le Kanban

// Configuration des ports de l'AVR
PORT_ACCESS(TXPin,B,1)			// communication serie sur le port B1
PORT_ACCESS(StartPin555,A,1)	// gachette du NE555 sur le port A1
//PORT_ACCESS(StartPin,A,0)


struct {
	uint8_t index;
	uint16_t result[50];
	} postitReaded;

void SoftUART_init()
{
	// configure la broche TX pour la communication serie
	set_TXPin_out();
	set_TXPin_high();
	
	// configure le Timer 0

	TCCR0A = 0b00000010; // mode CTC
	TCCR0B = 0b00000010; //Prescal 8
	OCR0A = 103; // 9600 bauds pour F_CPT 8MHz
	 
	
}

void SoftUART_send(char data)
{

	TIFR0 |= _BV(OCF0A);
	while ((TIFR0 & _BV(OCF0A))==0); // on se cale sur un début de cycle
	TIFR0 |= _BV(OCF0A);
	
	PORTB &= ~_BV(PORTB1); //envoie du bit start
	while ((TIFR0 & _BV(OCF0A))==0); //on attent le prochain cycle
	TIFR0 |= _BV(OCF0A);
	
	for(int i=0; i<8; i++)
	{
		if ((data & 1) == 0)
			set_TXPin_low(); //PORTB &= ~_BV(PORTB1);
		else
			set_TXPin_high(); //PORTB |= _BV(PORTB1);
			
		data >>= 1;
		while ((TIFR0 & _BV(OCF0A))==0); //on attent le prochain cycle
		TIFR0 |= _BV(OCF0A);
	}
	
	PORTB |= _BV(PORTB1);
	while ((TIFR0 & _BV(OCF0A))==0); //on attent le prochain cycle
	TIFR0 |= _BV(OCF0A);
	while ((TIFR0 & _BV(OCF0A))==0); //on attent le prochain cycle
	TIFR0 |= _BV(OCF0A);
}

void SoftUART_print(char *data)
{
	while(*data != 0)
	{
		SoftUART_send(*data++);
	
	}
}

void SoftUART_printVal(int val)
{
	char data[20];
	itoa(val, data, 10);
	SoftUART_print(data);
}

/****************************************
**  Détection du front montant
*******************************************/
void initPinChange()
{
	MCUCR |= _BV(ISC01)|_BV(ISC00);  // INT0 sur un frond montant
	GIMSK |= _BV(INT0);  // on active INT0
}

ISR(EXT_INT0_vect)
{
	//TODO protèger le dépassement du tableau
	postitReaded.result[postitReaded.index++] = TCNT1>>2;  // >>2 pour diviser par 4 la valeur de TCNT
}


/*****************************************
**  boucle principale
********************************************/  

int main(void)
{
	SoftUART_init();
	initPinChange();
	
	TCCR1B = 0b00000101; //Prescal 8
	
	//set_StartPin_out();
	set_StartPin555_out();
	
	/*initTimers();
	DDRB |= 1<<DDB1; //PORTB1 en sortie
	DDRB |= 1<<DDB2; //PORTB1 en sortie
	PORTB &= ~_BV(PORTB2);
*/
	
	sei();
	
	
    while(1)
    {
		   
		/*while(getTimerTicks(0) % 300 != 0);
		PORTB |= _BV(PORTB2);
		PORTB ^= _BV(PORTB1);
	   */
		
		// ouverture du message
		SoftUART_print("<");
		
		for (int i=1; i<=MAX_COL;i++ )
		{
			// Initialisation du traitement d'une nouvelle colonne
			postitReaded.index = 0;
			SoftUART_printVal(i);
			SoftUART_print(":");
		
			// initialisation d'un cycle en activant la gachette du NE555
			set_StartPin555_low();
			_delay_ms(1);
			set_StartPin555_high();
		
			// remise à zero du compteur de temps
			cli();
			TCNT1 = 0;
			sei();
		
			// attente de la réponse des postits
			_delay_ms(50);
		
		
		
			// generation du message à envoyer au sofware sur le port serie
			for (int j=0;j<postitReaded.index;j++)
			{
				SoftUART_printVal(postitReaded.result[j]);
				
				if(j < postitReaded.index -1) // on ne place pas de virgule si c'est le dernier element 
					SoftUART_print(",");
			}
		
			if (i <= MAX_COL-1) // on ne place pas de point virgule si c'est le dernier element 
				SoftUART_print(";");
		}
		
		SoftUART_print(">"); // fin du message
		
    }
}