package uk.co.crawj.week2;

import uk.co.crawj.utility.StarLevel;

public final class Dec8
{
    private Dec8()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        String[] grid = input.split( "\n" );
        
        int[][] treeMap = buildTreeGrid( grid );
        int[][] visibleMap = new int[treeMap.length][treeMap[0].length];
        
        if ( starLevel == StarLevel.STAR_1 )
        {
            return getVisibleTrees( treeMap, visibleMap );
        }
        else
        {
            return getMaxScenicScore( grid, treeMap );
        }
    }
    
    private static int getVisibleTrees( int[][] treeMap, int[][] visibleMap )
    {
        setVisibleRows( treeMap, visibleMap );
        setVisibleColumns( treeMap, visibleMap );
        
        int visibleCount = getVisibleCountFromGrid( visibleMap );
        visibleCount += countEdgeTrees( treeMap );
        return visibleCount;
    }
    
    private static int getMaxScenicScore( String[] grid, int[][] treeMap )
    {
        int max = 0;
        for ( int i = 0; i < grid.length; i++ )
        {
            for ( int j = 0; j < grid[0].length(); j++ )
            {
                max = Math.max( calculateScenicScore( treeMap, i, j ), max );
            }
        }
        return max;
    }
    
    private static int getVisibleCountFromGrid( int[][] visibleMap )
    {
        int count = 0;
        for ( int[] row : visibleMap )
        {
            for ( int point : row )
            {
                count += point;
            }
        }
        return count;
    }
    
    static int countEdgeTrees( int[][] treeMap )
    {
        int rowCount = treeMap[0].length;
        int columnCount = treeMap.length;
        
        return ( rowCount * 2 ) + ( ( columnCount - 2 ) * 2 );
    }
    
    static void setVisibleColumns( int[][] treeMap, int[][] visibleMap )
    {
        for ( int column = 1; column < treeMap.length; column++ )
        {
            int[] colAsIntArr = new int[treeMap.length];
            for ( int c = 0; c < treeMap.length; c++ )
            {
                colAsIntArr[c] = treeMap[c][column];
            }
            
            for ( int row = 0; row < treeMap[0].length - 2; row++ )
            {
                if ( treeMap[row + 1][column] > maxInRestOfValues( colAsIntArr, 0, row ) )
                {
                    setMapGridToVisible( visibleMap, row + 1, column );
                }
            }
            
            for ( int row = treeMap[0].length - 1; row > 0; row-- )
            {
                if ( treeMap[row - 1][column] > maxInRestOfValues( colAsIntArr, treeMap[0].length - 1, row ) )
                {
                    setMapGridToVisible( visibleMap, row - 1, column );
                }
            }
        }
        
    }
    
    static int maxInRestOfValues( int[] row, int start, int end )
    {
        int max = 0;
        for ( int i = Math.min( start, end ); i < Math.max( start, end ) + 1; i++ )
        {
            max = Math.max( max, row[i] );
        }
        return max;
    }
    
    static void setVisibleRows( int[][] treeMap, int[][] visibleMap )
    {
        for ( int j = 1; j < treeMap.length; j++ )
        {
            int[] treeRow = treeMap[j];
            for ( int i = 0; i < treeRow.length - 1; i++ )
            {
                if ( treeRow[i + 1] > maxInRestOfValues( treeRow, 0, i ) )
                {
                    setMapGridToVisible( visibleMap, j, i + 1 );
                }
            }
            for ( int i = treeRow.length - 1; i > 0; i-- )
            {
                if ( treeRow[i - 1] > maxInRestOfValues( treeRow, i, treeRow.length - 1 ) )
                {
                    setMapGridToVisible( visibleMap, j, i - 1 );
                }
            }
        }
    }
    
    private static void setMapGridToVisible( int[][] visibleMap, int x, int y )
    {
        if ( x == 0 || y == 0 || x == visibleMap.length - 1 || y == visibleMap[0].length - 1 )
        {
            return;
        }
        visibleMap[x][y] = 1;
    }
    
    static int[][] buildTreeGrid( String[] grid )
    {
        int[][] treeMap = new int[grid.length][grid[0].length()];
        
        for ( int j = 0; j < grid.length; j++ )
        {
            String trees = grid[j];
            char[] charArray = trees.toCharArray();
            for ( int i = 0; i < charArray.length; i++ )
            {
                char tree = charArray[i];
                treeMap[j][i] = tree - '0';
            }
        }
        
        return treeMap;
    }
    
    // this needs cleaning up
    static int calculateScenicScore( int[][] map, int x, int y )
    {
        int heightOfTree = map[x][y];
        int top = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        
        boolean maxTop = false;
        boolean maxBottom = false;
        boolean maxLeft = false;
        boolean maxRight = false;
        
        for ( int i = 1; i < map.length; i++ )
        {
            if ( !maxLeft && ( y - i >= 0 ) )
            {
                left++;
                if ( map[x][y - i] >= heightOfTree )
                {
                    maxLeft = true;
                }
            }
            else
            {
                maxLeft = true;
            }
            
            if ( !maxRight && ( y + i <= map.length - 1 ) )
            {
                right++;
                if ( map[x][y + i] >= heightOfTree )
                {
                    maxRight = true;
                }
            }
            else
            {
                maxRight = true;
            }
            
            if ( !maxTop && ( x - i >= 0 ) )
            {
                top++;
                if ( map[x - i][y] >= heightOfTree )
                {
                    maxTop = true;
                }
            }
            else
            {
                maxTop = true;
            }
            
            if ( !maxBottom && ( x + i <= map.length - 1 ) )
            {
                bottom++;
                if ( map[x + i][y] >= heightOfTree )
                {
                    maxBottom = true;
                }
            }
            else
            {
                maxBottom = true;
            }
        }
        
        return left * right * top * bottom;
    }
    
}
