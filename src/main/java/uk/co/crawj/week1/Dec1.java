package uk.co.crawj.week1;

import java.util.Arrays;

import uk.co.crawj.utility.StarLevel;

public final class Dec1
{
    
    private Dec1()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        String[] elfLoad = input.split( "\n\n" );
        
        int[] totals = Arrays.stream( elfLoad )
                .mapToInt( Dec1::getSumForElf )
                .sorted()
                .skip( elfLoad.length - 3L ) // keeps heaviest 3 values
                .toArray();
        
        return switch ( starLevel )
                {
                    case STAR_1 -> totals[2];
                    case STAR_2 -> Arrays.stream( totals ).sum();
                };
    }
    
    private static int getSumForElf( String elf )
    {
        String[] loads = elf.split( "\n" );
        return Arrays.stream( loads ).mapToInt( Integer::parseInt ).sum();
    }
    
}
