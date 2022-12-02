package uk.co.crawj.week1;

import uk.co.crawj.utility.StarLevel;

public class Dec2
{
    public static int code( String input, StarLevel starLevel )
    {
        String[] rounds = input.split( "\n" );
        
        int score = 0;
        for ( String round : rounds )
        {
            if ( starLevel == StarLevel.STAR_1 )
            {
                score += calculateScore( round );
            }
            else if ( starLevel == StarLevel.STAR_2 )
            {
                score += calculateScoreFromResult( round );
            }
        }
        return score;
    }
    
    static int calculateScoreFromResult( String round )
    {
        int actionPoints;
        
        if ( round.equals( "A X" ) || round.equals( "B Z" ) || round.equals( "C Y" ) )
        {
            actionPoints = Action.SCISSORS.score;
        }
        else if ( round.equals( "A Y" ) || round.equals( "B X" ) || round.equals( "C Z" ) )
        {
            actionPoints = Action.ROCK.score;
        }
        else
        {
            actionPoints = Action.PAPER.score;
        }
        
        int y = round.contains( "Y" ) ? Result.DRAW.score : Result.WIN.score;
        int actionScore = round.contains( "X" ) ? Result.LOSE.score : y;
        return actionPoints + actionScore;
    }
    
    static int calculateScore( String round )
    {
        String[] actions = round.split( " " );
        Action action = Action.getAction( actions[1] );
        int actionScore = action.score;
        
        int resultScore;
        if ( round.equals( "A X" ) || round.equals( "B Y" ) || round.equals( "C Z" ) )
        {
            resultScore = Result.DRAW.score;
        }
        else if ( round.equals( "A Z" ) || round.equals( "B X" ) || round.equals( "C Y" ) )
        {
            resultScore = Result.LOSE.score;
        }
        else
        {
            resultScore = Result.WIN.score;
        }
        
        return actionScore + resultScore;
    }
    
    enum Result
    {
        WIN( 6 ),
        DRAW( 3 ),
        LOSE( 0 );
        
        private final int score;
        
        Result( int score )
        {
            this.score = score;
        }
    }
    
    enum Action
    {
        ROCK( 1, "X" ),
        PAPER( 2, "Y" ),
        SCISSORS( 3, "Z" );
        
        private final int score;
        
        private final String yourMove;
        
        Action( int score, String yourMove )
        {
            this.score = score;
            this.yourMove = yourMove;
        }
        
        public static Action getAction( String value )
        {
            for ( Action v : values() )
            {
                if ( v.yourMove.equalsIgnoreCase( value ) )
                {
                    return v;
                }
            }
            throw new IllegalArgumentException();
        }
    }
}
