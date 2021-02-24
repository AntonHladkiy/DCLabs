package main

import (
	"fmt"
	"strconv"
	"time"
)

var goodsCount int = 5

func ivanovTake(takenGoodsChan chan int) {
	for i := 0; i < goodsCount; i++ {
		fmt.Println("Ivanov took a new good from military base.")
		takenGoodsChan <- 1
		time.Sleep(1 * time.Microsecond)
	}
}

func petrovLoad(takenGoodsChan chan int, loadedGoodsChan chan int) {
	for i := 0; i < goodsCount; i++ {
		good := <-takenGoodsChan
		fmt.Println("Petrov loaded a new good into a truck.")
		loadedGoodsChan <- good
		time.Sleep(1 * time.Microsecond)
	}
}

func nechiporchukCountPrice(loadedGoodsChan chan int) {
	var sum = 0
	for i := 0; i < goodsCount; i++ {
		good := <-loadedGoodsChan
		sum += good
		fmt.Println("Nechiporchuk added a new good price to the total price. Total price is: " + strconv.Itoa(sum))
		time.Sleep(1 * time.Microsecond)
	}
}

func main() {
	var takenGoodsChan = make(chan int, 1)
	var loadedGoodsChan = make(chan int, 1)

	go ivanovTake(takenGoodsChan)
	go petrovLoad(takenGoodsChan, loadedGoodsChan)
	go nechiporchukCountPrice(loadedGoodsChan)

	fmt.Scanln()
}