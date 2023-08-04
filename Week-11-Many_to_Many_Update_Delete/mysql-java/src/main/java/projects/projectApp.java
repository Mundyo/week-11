package projects;
	

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.Exception.DbException;
import projects.entity.Project;
import projects.service.projectService;

public class projectApp {
	private Scanner scanner= new Scanner(System.in);
	private projectService projectService = new projectService();
	private Project curProject;
//    @formatter:off
	private List<String> operations = List.of(
			"1)Add a project",
			"2) List project",
			"3) Select a project",
			"4) Update project details",
			"5) delete a Project"
			);
//   @formatter:on 

    

    public static void main(String[] args) {
       new projectApp().processUserSelections();
    }

    @SuppressWarnings("unused")
	private void processUserSelections() {
        boolean done = false;
        while (!done) {
            try {
                int selection = getUserSelection();
                switch (selection) {
                    case -1:
                        done = exitMenu();
                        break;
                    case 1:
                    	createProject();
                    	break;
                    
                    case 2:
                    	ListProjects();
                    	break;
                    	
                    case 3:
                    	selectProject();
                    	break;
                    	
                    case 4: 
                    	updateProjectDtails();
                    	break;
                    	
                    case 5:
                    	deleteProject();
                    	break;
                    	
                    default:
                        System.out.println("\n" + selection + " is not a valid selection. Try again.");
//                         break;
                }
            }
             catch (Exception e) {
               System.out.println("Error: " + e.toString());
                

            }
        }
    }
    
    
    private void deleteProject() {
    	ListProjects();
    	
    	Integer projectId = getIntInput("Enter the ID of the project to delete");
    	
    	projectService.deleteProject(projectId);
    	System.out.println("Project " + projectId + " was deleted successfully.");
    	
    	if(Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
    		curProject = null;
    	}
    }
    
    
    
    private void updateProjectDtails() {

		if (Objects.isNull(curProject)) {;
		System.out.println("\nPlease select a project.");
		return;
		}
	   String projectName =
			   getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
	   
	   BigDecimal estimatedHours =
			   getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
	
	   BigDecimal actualHours=
			   getDecimalInput("Enter the actual hours + [" + curProject.getDifficulty() +"]");
	   
	   Integer difficulty=
			   getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() +"]");
	   
	   String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
	   
	   Project project = new Project();
	   
	   project.setProjectId(curProject.getProjectId());
	   project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
	   
	   project.setEstimatedHours(
			   Objects.isNull(estimatedHours) ? curProject.getEstimatedHours() : estimatedHours);
	   
	   project.setActualHours(Objects.isNull(actualHours) ? curProject.getActualHours(): actualHours);
       project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty() : difficulty);
	   project.setNotes(Objects.isNull(notes) ? curProject.getNotes() : notes);
	   
	   projectService.modifyProjectDetails(project);
	   
	   curProject =projectService.fetchProjectById(curProject.getProjectId());
    }

    
    
	private void selectProject() {
		// TODO Auto-generated method stub
		ListProjects();
		Integer projectId = getIntInput("Enter a project ID to select a project");
		
		curProject = null;
		
		curProject = projectService.fetchProjectById(projectId);
	}

	private void ListProjects() {
		// TODO Auto-generated method stub
		List<Project> projects = projectService.fetchAllprojects();
		
		System.out.println("\nProjects:");
		
		projects.forEach(project -> System.out
			.println("  " + project.getProjectId()
		        +": "+project.getProjectName()));
	}

	private void createProject() {
        String projectName = getStringInput("Enter the project name");
        BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
        BigDecimal actualHours = getDecimalInput("Enter the actual hours");
        Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
        String notes = getStringInput("Enter the project notes");

        Project project = new Project();
        
        project.setProjectName(projectName);
        project.setEstimatedHours(estimatedHours);
        project.setActualHours(actualHours);
        project.setDifficulty(difficulty);
        project.setNotes(notes);
        
        Project dbProject = projectService.addProject(project);
        System.out.println("You have successfully created project: " + dbProject);
    }
    
    private BigDecimal getDecimalInput(String prompt) {
        String input = getStringInput(prompt);
        if (Objects.isNull(input)) {
            return null;
        }
        try {
            return new BigDecimal(input).setScale(2);
        } catch (NumberFormatException e) {
            throw new DbException(input + " is not a valid decimal number. Try again.");
        }
    }

    
    private boolean exitMenu() {
        System.out.println("Exiting menu...");
        return true;
    }
    
    
    
    private int getUserSelection() {
        printOperations();
        
        Integer input = getIntInput("Enter a menu selection");
        
        return Objects.isNull(input) ? -1 : input;
    }
    

    
    private Integer getIntInput(String prompt) {
        String input = getStringInput(prompt);
        if (Objects.isNull(input)) {
            return null;
        }
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            throw new DbException(input + " is not a valid number. Try again.");
        }
    }
    
    
    
    private String getStringInput(String prompt) {
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();
        return input.trim().isEmpty() ? null : input.trim();
    }
    
    
    
    private void printOperations() {
        System.out.println("\n these are the available selections. Press Enter key to quit:");
        
        operations.forEach(line -> System.out.println("  " + line));
        
        if (Objects.isNull(curProject)) {
        	System.out.println("\nYou are  not working with a project.");
        }
        else {
        	System.out.println("\nYou are working with project: "+ curProject);
        }
    }
    
  
}
    


   


    
    
  

   



