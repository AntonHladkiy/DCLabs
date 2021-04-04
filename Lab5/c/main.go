package main

import (
	"fmt"
	"time"
)

func simulate(){
	var sem = make(chan int, 5)
	for i := 1; i < 10; i++ {
		go bus_stop(sem,i) //5 buss can stop at the same time only
	}
}

func bus_stop(sem chan int,id int){
	fmt.Printf("Bus number %#v is waiting for space \n",id)
	sem <- 1
	fmt.Printf("Bus number %#v stopped at bus stop \n",id)
	time.Sleep(10*time.Second)
	<-sem
	fmt.Printf("Bus number %#v drive from bus stop \n",id)
}

func main() {
	simulate()

	fmt.Scanln()
}