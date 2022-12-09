package uk.co.crawj.week1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import uk.co.crawj.utility.StarLevel;

public class Dec7
{
    
    public static final int MAX_SIZE = 100000;
    
    private static final List<Integer> dirsWithSmallSize = new ArrayList<>();
    
    private static final Set<Integer> allDirSizes = new HashSet<>();
    
    private Dec7()
    {
        throw new IllegalStateException( "Utility Class" );
    }
    
    public static int code( String input, StarLevel starLevel )
    {
        dirsWithSmallSize.clear();
        allDirSizes.clear();
        Directory root = new Directory( "/", null );
        
        String[] instructions = input.split( "\n" );
        
        Directory currentDir = root;
        for ( String instr : instructions )
        {
            if ( instr.startsWith( "$ cd" ) && !instr.contains( "/" ) )
            {
                currentDir = moveDir( instr, currentDir );
            }
            if ( instr.startsWith( "dir" ) )
            {
                createDir( instr, currentDir );
            }
            else if ( !instr.startsWith( "$" ) )
            {
                createFile( instr, currentDir );
            }
        }
        
        int size = getSize( root );
        if ( starLevel == StarLevel.STAR_1 )
        {
            return size;
        }
        
        return findSmallestDirThatWorks( root );
    }
    
    private static int findSmallestDirThatWorks( Directory root )
    {
        int totalDiskSpace = 70000000;
        int unusedSpaceRequired = 30000000;
        int usedSpace = calculateSize( root );
        int unusedSpace = totalDiskSpace - usedSpace;
        int dirMustBeBiggerThan = unusedSpaceRequired - unusedSpace;
        
        int smallestValidDirToDelete = 30000000;
        
        allDirSizes.removeIf( i -> i == 0 || i == usedSpace );
        
        for ( Integer dir : allDirSizes )
        {
            if ( dir > dirMustBeBiggerThan )
            {
                smallestValidDirToDelete = Math.min( dir, smallestValidDirToDelete );
            }
        }
        
        return smallestValidDirToDelete;
    }
    
    private static int getSize( Directory directory )
    {
        int root = calculateSize( directory );
        allDirSizes.add( root );
        if ( root < MAX_SIZE )
        {
            dirsWithSmallSize.add( root );
            allDirSizes.add( root );
        }
        
        for ( Directory subDir : directory.getSubDirs() )
        {
            int dirSize = calculateSize( subDir );
            allDirSizes.add( dirSize );
            if ( dirSize <= MAX_SIZE )
            {
                dirsWithSmallSize.add( dirSize );
            }
            
            for ( Directory subSubDir : subDir.getSubDirs() )
            {
                getSize( subSubDir );
            }
        }
        return dirsWithSmallSize.stream().reduce( 0, Integer::sum );
    }
    
    private static int calculateSize( Directory directory )
    {
        int size = 0;
        
        for ( File file : directory.getFiles() )
        {
            size += file.size();
        }
        
        for ( Directory subDir : directory.getSubDirs() )
        {
            size += calculateSize( subDir );
        }
        
        return size;
    }
    
    private static Directory moveDir( String instruction, Directory currentDir )
    {
        String dirName = instruction.split( " " )[2];
        
        if ( dirName.equals( ".." ) )
        {
            return currentDir.getParentDir();
        }
        else
        {
            for ( Directory subDir : currentDir.getSubDirs() )
            {
                if ( subDir.getName().equals( dirName ) )
                {
                    return subDir;
                }
            }
        }
        
        return currentDir;
    }
    
    private static void createDir( String instruction, Directory currentDir )
    {
        String[] directoryDetails = instruction.split( " " );
        Directory directory = new Directory( directoryDetails[1], currentDir );
        currentDir.addSubDir( directory );
    }
    
    private static void createFile( String instruction, Directory currentDir )
    {
        String[] fileDetails = instruction.split( " " );
        currentDir.addFile( new File( fileDetails[1], Integer.parseInt( fileDetails[0] ) ) );
    }
    
    private static class Directory
    {
        String name;
        
        Directory parentDir;
        
        List<Directory> subDirs;
        
        List<File> files;
        
        Directory( String name, Directory parentDir )
        {
            this.name = name;
            this.parentDir = parentDir;
        }
        
        public Directory getParentDir()
        {
            return parentDir;
        }
        
        public String getName()
        {
            return name;
        }
        
        public List<Directory> getSubDirs()
        {
            return Objects.requireNonNullElseGet( subDirs, ArrayList::new );
        }
        
        public void addSubDir( Directory directory )
        {
            if ( subDirs == null )
            {
                subDirs = new ArrayList<>();
            }
            subDirs.add( directory );
        }
        
        public List<File> getFiles()
        {
            return Objects.requireNonNullElseGet( files, ArrayList::new );
        }
        
        public void addFile( File file )
        {
            if ( files == null )
            {
                files = new ArrayList<>();
            }
            files.add( file );
        }
    }
    
    private record File(String name, Integer size)
    {
    }
    
}
