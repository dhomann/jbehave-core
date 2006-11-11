package com.sirenian.hellbound.domain;
import jbehave.core.mock.UsingConstraints;

import com.sirenian.hellbound.domain.Segments;

public class SegmentsBehaviour extends UsingConstraints {

	public void shouldMoveEachSegmentRight() {
		Segments segments = new Segments(new Segment[] {
				new Segment(0, 1),
				new Segment(0, 2),
				new Segment(0, 3)
		});
		
		Segments movedSegments = segments.movedRight();
		
		ensureThat(movedSegments.get(0), eq(new Segment(1, 1)));
		ensureThat(movedSegments.get(1), eq(new Segment(1, 2)));
		ensureThat(movedSegments.get(2), eq(new Segment(1, 3)));
	}
	
	public void shouldMoveEachSegmentRightByOffset() {
		Segments segments = new Segments(new Segment[] {
				new Segment(0, 1),
				new Segment(0, 2),
				new Segment(0, 3)
		});
		
		Segments movedSegments = segments.movedRight(3);
		
		ensureThat(movedSegments.get(0), eq(new Segment(3, 1)));
		ensureThat(movedSegments.get(1), eq(new Segment(3, 2)));
		ensureThat(movedSegments.get(2), eq(new Segment(3, 3)));
	}	
	
	public void shouldMoveEachSegmentLeft() {
		Segments segments = new Segments(new Segment[] {
				new Segment(1, 1),
				new Segment(1, 2),
				new Segment(1, 3)
		});
		
		Segments movedSegments = segments.movedLeft();
		
		ensureThat(movedSegments.get(0), eq(new Segment(0, 1)));
		ensureThat(movedSegments.get(1), eq(new Segment(0, 2)));
		ensureThat(movedSegments.get(2), eq(new Segment(0, 3)));
	}
	
	public void shouldMoveEachSegmentDown() {
		Segments segments = new Segments(new Segment[] {
				new Segment(0, 1),
				new Segment(0, 2),
				new Segment(0, 3)
		});
		
		Segments movedSegments = segments.movedDown();
		
		ensureThat(movedSegments.get(0), eq(new Segment(0, 2)));
		ensureThat(movedSegments.get(1), eq(new Segment(0, 3)));
		ensureThat(movedSegments.get(2), eq(new Segment(0, 4)));
	}
	
	public void shouldBeEqualToSegmentsWithSameCoordinatesAndOrder() {
		Segments segments1 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		Segments segments2 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		
		Segments segments3 = new Segments(
				new Segment(0, 4), 
				new Segment(0, 3), 
				new Segment(0, 2), 
				new Segment(0, 1));
		
		ensureThat(segments1, eq(segments2));
		ensureThat(segments2, eq(segments1));
		ensureThat(segments3, not(eq(segments1)));
		
		ensureThat(segments1.hashCode(), eq(segments2.hashCode()));
	}
	
	public void shouldHaveSizeEqualToNumberOfSegments() {
		Segments segments1 = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		Segments segments2 = new Segments(new Segment[] {
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3)});
		
		ensureThat(segments1.size(), eq(4));
		ensureThat(segments2.size(), eq(3));
	}
	
	public void shouldContainSegmentsWithWhichItWasConstructed() {
		Segments segments = new Segments(
				new Segment(0, 1), 
				new Segment(0, 2), 
				new Segment(0, 3), 
				new Segment(0, 4));
		
		ensureThat(segments.contains(new Segment(0, 1)));
		ensureThat(!segments.contains(new Segment(0, 5)));
	}
}