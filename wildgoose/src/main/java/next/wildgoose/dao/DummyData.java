package next.wildgoose.dao;

import java.util.Random;

import org.springframework.stereotype.Component;

import next.wildgoose.dto.StatPoints;

@Component
public class DummyData{
	
	public StatPoints getStatPoints(int reporterId) {
		StatPoints statPoints = new StatPoints();
		Random random = new Random();
		
		statPoints.setStat("꾸준함", random.nextInt(101)/10);
		statPoints.setStat("받아씀", random.nextInt(101)/10);
		statPoints.setStat("어그로", random.nextInt(101)/10);
		statPoints.setStat("강태공", random.nextInt(101)/10);
		statPoints.setStat("잡식성", random.nextInt(101)/10);
		statPoints.setStat("정의감", random.nextInt(101)/10);
		statPoints.setStat("먹튀", random.nextInt(101)/10);
		
		return statPoints;
		
	}

}
