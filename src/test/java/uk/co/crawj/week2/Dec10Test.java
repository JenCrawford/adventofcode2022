package uk.co.crawj.week2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.co.crawj.utility.StarLevel;

class Dec10Test
{
    String test1 = """
            addx 15
            addx -11
            addx 6
            addx -3
            addx 5
            addx -1
            addx -8
            addx 13
            addx 4
            noop
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx 5
            addx -1
            addx -35
            addx 1
            addx 24
            addx -19
            addx 1
            addx 16
            addx -11
            noop
            noop
            addx 21
            addx -15
            noop
            noop
            addx -3
            addx 9
            addx 1
            addx -3
            addx 8
            addx 1
            addx 5
            noop
            noop
            noop
            noop
            noop
            addx -36
            noop
            addx 1
            addx 7
            noop
            noop
            noop
            addx 2
            addx 6
            noop
            noop
            noop
            noop
            noop
            addx 1
            noop
            noop
            addx 7
            addx 1
            noop
            addx -13
            addx 13
            addx 7
            noop
            addx 1
            addx -33
            noop
            noop
            noop
            addx 2
            noop
            noop
            noop
            addx 8
            noop
            addx -1
            addx 2
            addx 1
            noop
            addx 17
            addx -9
            addx 1
            addx 1
            addx -3
            addx 11
            noop
            noop
            addx 1
            noop
            addx 1
            noop
            noop
            addx -13
            addx -19
            addx 1
            addx 3
            addx 26
            addx -30
            addx 12
            addx -1
            addx 3
            addx 1
            noop
            noop
            noop
            addx -9
            addx 18
            addx 1
            addx 2
            noop
            noop
            addx 9
            noop
            noop
            noop
            addx -1
            addx 2
            addx -37
            addx 1
            addx 3
            noop
            addx 15
            addx -21
            addx 22
            addx -6
            addx 1
            noop
            addx 2
            addx 1
            noop
            addx -10
            noop
            noop
            addx 20
            addx 1
            addx 2
            addx 2
            addx -6
            addx -11
            noop
            noop
            noop""";
    
    String actualInput = """
            noop
            addx 10
            addx -4
            addx -1
            noop
            noop
            addx 5
            addx -12
            addx 17
            noop
            addx 1
            addx 2
            noop
            addx 3
            addx 2
            noop
            noop
            addx 7
            addx 3
            noop
            addx 2
            noop
            noop
            addx 1
            addx -38
            addx 5
            addx 2
            addx 3
            addx -2
            addx 2
            addx 5
            addx 2
            addx -4
            addx 26
            addx -19
            addx 2
            addx 5
            addx -2
            addx 7
            addx -2
            addx 5
            addx 2
            addx 4
            addx -17
            addx -23
            addx 1
            addx 5
            addx 3
            noop
            addx 2
            addx 24
            addx 4
            addx -23
            noop
            addx 5
            addx -1
            addx 6
            noop
            addx -2
            noop
            noop
            noop
            addx 7
            addx 1
            addx 4
            noop
            noop
            noop
            noop
            addx -37
            addx 5
            addx 2
            addx 1
            noop
            addx 4
            addx -2
            addx -4
            addx 9
            addx 7
            noop
            noop
            addx 2
            addx 3
            addx -2
            noop
            addx -12
            addx 17
            noop
            addx 3
            addx 2
            addx -3
            addx -30
            addx 3
            noop
            addx 2
            addx 3
            addx -2
            addx 2
            addx 5
            addx 2
            addx 11
            addx -6
            noop
            addx 2
            addx -19
            addx 20
            addx -7
            addx 14
            addx 8
            addx -7
            addx 2
            addx -26
            addx -7
            noop
            noop
            addx 5
            addx -2
            addx 5
            addx 15
            addx -13
            addx 5
            noop
            noop
            addx 1
            addx 4
            addx 3
            addx -2
            addx 4
            addx 1
            noop
            addx 2
            noop
            addx 3
            addx 2
            noop
            noop
            noop
            noop
            noop""";
    
    @Test
    void test()
    {
        assertEquals( 13140, Dec10.code( test1, StarLevel.STAR_1 ) );
        assertEquals( 16060, Dec10.code( actualInput, StarLevel.STAR_1 ) );
        Dec10.code( test1, StarLevel.STAR_1 );
        Dec10.updateSpriteLocation( "reset" );
        Dec10.code( actualInput, StarLevel.STAR_2 );
    }
    
    @Test
    void updateSprint()
    {
        Dec10.X = 1;
        Dec10.updateSpriteLocation( "reset" );
        assertEquals( "........................................", Dec10.spriteLocation );
        Dec10.updateSpriteLocation( "noop" );
        assertEquals( "........................................", Dec10.spriteLocation );
        Dec10.updateSpriteLocation( "addx 15" );
        assertEquals( "...............###......................", Dec10.spriteLocation );
        Dec10.updateSpriteLocation( "addx -10" );
        assertEquals( ".....###................................", Dec10.spriteLocation );
    }
}