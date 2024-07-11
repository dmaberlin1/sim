package com.dmadev.sim.creature;

import com.dmadev.sim.base.Creature;
import com.dmadev.sim.map.Coordinates;
import com.dmadev.sim.map.GameMap;



public final class Predator extends Creature {
    private  final int power;

    public Predator(Coordinates coordinates,int power) {
        super(coordinates);
        this.power=power;
    }


    public void eat(Coordinates start, Coordinates to, GameMap gameMap){

        Creature herbivore=(Creature) gameMap.getEntity(to);
        // Рассчитываем оставшееся здоровье жертвы
        int remainingHealth = herbivore.getHealth() - power;

        if(remainingHealth <=0){
            super.eat(start,to,gameMap);
        }else{
            herbivore.setHealth(remainingHealth);
        }


    }

    //Хищник, наследуется от Creature. В дополнение к полям класса Creature, имеет силу атаки.
    // На что может потратить ход хищник:
    //Переместиться (чтобы приблизиться к жертве - травоядному)
    //Атаковать травоядное. При этом количество HP травоядного уменьшается на силу атаки хищника.
    //Если значение HP жертвы опускается до 0, травоядное исчезает




}
