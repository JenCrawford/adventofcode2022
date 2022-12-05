package uk.co.crawj.week1;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

import uk.co.crawj.utility.StarLevel;

public class Dec4
{
    private Dec4()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        String[] elves = input.split( "\n" );
        
        int assignmentPairs = 0;
        for ( String pair : elves )
        {
            String elf1 = pair.split( "," )[0];
            String elf2 = pair.split( "," )[1];
            
            String[] jobs = elf1.split( "-" );
            String[] jobs2 = elf2.split( "-" );
            
            List<Integer> sections1 =
                    IntStream.range( Integer.parseInt( jobs[0] ), Integer.parseInt( jobs[1] ) + 1 ).boxed().toList();
            List<Integer> sections2 =
                    IntStream.range( Integer.parseInt( jobs2[0] ), Integer.parseInt( jobs2[1] ) + 1 ).boxed().toList();
            
            boolean result;
            if ( starLevel == StarLevel.STAR_1 )
            {
                result = new HashSet<>( sections1 ).containsAll( sections2 ) || new HashSet<>( sections2 ).containsAll( sections1 );
            }
            else
            {
                result = sections1.stream().anyMatch( sections2::contains ) || sections2.stream().anyMatch( sections1::contains );
            }
            
            if ( result )
            {
                assignmentPairs++;
            }
        }
        
        return assignmentPairs;
    }
    
}
