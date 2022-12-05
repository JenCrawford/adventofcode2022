package uk.co.crawj.week1;

import uk.co.crawj.utility.StarLevel;

public class Dec3
{
    private Dec3()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        String[] rucksacks = input.split( "\n" );
        
        if ( starLevel == StarLevel.STAR_1 )
        {
            return getSumForStar1( rucksacks );
        }
        else
        {
            return getSumForStar2( rucksacks );
        }
    }
    
    private static int getSumForStar2( String[] rucksacks )
    {
        int prioritySum = 0;
        
        for ( int i = 0; i < rucksacks.length - 2; i += 3 )
        {
            String rs1 = rucksacks[i];
            String rs2 = rucksacks[i + 1];
            String rs3 = rucksacks[i + 2];
            
            prioritySum += getBadgeFromRucksacks( rs1, rs2, rs3 );
        }
        return prioritySum;
    }
    
    private static int getBadgeFromRucksacks( String rs1, String rs2, String rs3 )
    {
        for ( char c : rs1.toCharArray() )
        {
            for ( char d : rs2.toCharArray() )
            {
                for ( char e : rs3.toCharArray() )
                {
                    if ( c == d && d == e )
                    {
                        return getPriority( c );
                    }
                }
            }
        }
        
        return 0;
    }
    
    private static int getSumForStar1( String[] rucksacks )
    {
        int prioritySum = 0;
        for ( String rucksack : rucksacks )
        {
            String compartment1 = rucksack.substring( 0, rucksack.length() / 2 );
            String compartment2 = rucksack.substring( rucksack.length() / 2 );
            
            prioritySum += getPrioritySumForRucksack( compartment1, compartment2 );
            
        }
        return prioritySum;
    }
    
    private static int getPrioritySumForRucksack( String compartment1, String compartment2 )
    {
        for ( char c : compartment1.toCharArray() )
        {
            for ( char d : compartment2.toCharArray() )
            {
                if ( c == d )
                {
                    return getPriority( c );
                }
            }
        }
        return 0;
    }
    
    static int getPriority( char c )
    {
        if ( c < 91 )
        {
            return c - 65 + 27;
        }
        
        return c - 96;
    }
}
