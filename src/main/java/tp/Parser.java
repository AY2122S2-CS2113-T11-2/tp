package tp;

import java.util.Scanner;

public class Parser {

    public Parser() {

    }

    public static String getCommand() {
        String command;
        Scanner in = new Scanner(System.in);
        command = in.nextLine();
        return command;
    }

    public Command parseAddDoctor(String fullCommand) throws IHospitalException {
        String id;
        String dummy = fullCommand.trim();
        if (dummy.indexOf("/id") > dummy.indexOf("/n") || dummy.indexOf("/id") > dummy.indexOf("/ph")
                    || dummy.indexOf("/id") > dummy.indexOf("/e") || dummy.indexOf("/n") > dummy.indexOf("/ph")
                    || dummy.indexOf("/n") > dummy.indexOf("/e") || dummy.indexOf("/ph") > dummy.indexOf("/e")) {
            throw new IHospitalException("The format of input is wrong, can check the order");
        }

        int idIndex = dummy.indexOf("/id") + 4;
        int nameIndex = dummy.indexOf("/n");
        id = dummy.substring(idIndex, nameIndex).trim();
        nameIndex += 3;
        int phoneNumberIndex = dummy.indexOf("/ph");
        String name = dummy.substring(nameIndex, phoneNumberIndex).trim();
        phoneNumberIndex += 4;
        int emailIndex = dummy.indexOf("/e");
        String phoneNumber = dummy.substring(phoneNumberIndex, emailIndex).trim();
        emailIndex += 3;
        String email = dummy.substring(emailIndex).trim();
        return new AddDoctorCommand(id, name, phoneNumber, email, false);
    }

    public Command parseGetAppointment(String fullCommand) throws IHospitalException {
        //get appointment /d 123456
        String dummy = fullCommand.trim();
        String id =  dummy.substring(dummy.indexOf("/d") + 3).trim();
        return new GetAppointmentsOfDoctorCommand(id);
    }

    public Command parseAddPatient(String fullCommand) throws IHospitalException {
        String id;
        String dummy = fullCommand.trim();
        String name;
        int idIndex = dummy.indexOf("/id") + 4;
        int nameIndex = dummy.indexOf("/n");
        id = dummy.substring(idIndex, nameIndex).trim();
        nameIndex += 3;
        int phoneNumberIndex = dummy.indexOf("/ph");
        name = dummy.substring(nameIndex, phoneNumberIndex).trim();
        phoneNumberIndex += 4;
        int emailIndex = dummy.indexOf("/e");
        String phoneNumber = dummy.substring(phoneNumberIndex, emailIndex).trim();
        emailIndex += 3;
        int symptomIndex = dummy.indexOf("/s");
        String email = dummy.substring(emailIndex,symptomIndex).trim();
        symptomIndex += 3;
        String phone = phoneNumber;
        int descIndex = dummy.indexOf("/d");
        String symptom = dummy.substring(symptomIndex,descIndex).trim();
        descIndex += 3;
        String description = dummy.substring(descIndex).trim();
        return new AddPatientCommand(id, name, phone, email, symptom,description);
    }

    public Command parseAddAppointment(String fullCommand) throws IHospitalException {
        String time;
        String dummy = fullCommand.trim();
        int timeIndex = dummy.indexOf("/t");
        int doctorIndex = dummy.indexOf("/d");
        timeIndex += 3;
        time = dummy.substring(timeIndex, doctorIndex).trim();
        int patientIndex = dummy.indexOf("/p");
        doctorIndex += 3;
        String s = dummy.substring(doctorIndex, patientIndex).trim();

        doctorIndex = Integer.parseInt(s);
        patientIndex += 3;
        s = dummy.substring(patientIndex).trim();
        patientIndex = Integer.parseInt(s);
        return new AddAppointmentCommand(doctorIndex, patientIndex, time);
    }

    public Command parseAddPatientDescription(String fullCommand) throws IHospitalException {
        String dummy = fullCommand.trim();
        int patientIndex = dummy.indexOf("/p");
        int descriptionIndex = dummy.indexOf("/d");
        int patientID = Integer.parseInt(dummy.substring(patientIndex, descriptionIndex).trim());
        descriptionIndex += 3;
        String description = dummy.substring(descriptionIndex);
        return new AddPatientDescriptionCommand(description, patientID);
    }

    public Command parseAddCommand(String fullCommand) throws IHospitalException {
        if (fullCommand.contains("add doctor")) {
            return parseAddDoctor(fullCommand);
        } else if (fullCommand.contains("add patient")) {
            try {
                return parseAddPatient(fullCommand);
            } catch (Exception e) {
                System.out.println("The input format of the patient information is wrong.");
            }
        } else if (fullCommand.contains("add appointment")) {
            return parseAddAppointment(fullCommand);
        } else if (fullCommand.contains("add patient description")) {
            return parseAddPatientDescription(fullCommand);
        }
        return null;
    }

    public Command parseDeleteDoctor(String fullCommand) throws IHospitalException{
        String dummy[] = fullCommand.split(" ");
        if (dummy.length <= 2) {
            throw new IHospitalException("Please enter the id of the doctor you want to delete");
        }
        else{
            int index = Integer.parseInt(dummy[2]);
            return new DeleteDoctorCommand(index);
        }
    }

    public Command parseDeletePatient(String fullCommand) throws IHospitalException{
        String dummy[] = fullCommand.split(" ");
        if (dummy.length <= 2) {
            throw new IHospitalException("Please enter the id of the patient you want to delete");
        }
        else{
            int index = Integer.parseInt(dummy[2]);
            return new DeletePatientCommand(index);
        }
    }

    public Command parseDeleteAppointment(String fullCommand) throws IHospitalException{
        String dummy[] = fullCommand.split(" ");
        if (dummy.length <= 2) {
            throw new IHospitalException("Please enter the id of the appointment you want to delete");
        }
        else{
            int index = Integer.parseInt(dummy[2]);
            return new DeleteAppointmentCommand(index);
        }
    }


    public Command parseDeleteCommand(String fullCommand) throws IHospitalException {
        if (fullCommand.contains("delete doctor")) {
            try {
                return parseDeleteDoctor(fullCommand);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (fullCommand.contains("delete patient")) {
            try {
                return parseDeletePatient(fullCommand);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (fullCommand.contains("delete appointment")) {
            try {
                return parseDeleteAppointment(fullCommand);
            } catch (Exception e) {
                System.out.println(e);
            }
        }else{
            throw new IHospitalException("Please enter whether you want to delete a doctor, patient or appointment");
        }
        return null;
    }

    public Command parseSearchCommand(String fullCommand) throws IHospitalException {
        if (fullCommand.contains("doctor")) {
            String dummy = fullCommand.trim();
            String index = dummy.substring(dummy.indexOf("doctor ") + 7);
            return new SearchDoctorCommand(index);
        } else if (fullCommand.contains("patient")) {
            String dummy = fullCommand.trim();
            String index = dummy.substring(dummy.indexOf("patient ") + 8);
            return new SearchPatientCommand(index);
        } else if (fullCommand.contains("appointment")) {
            String dummy = fullCommand.trim();
            String time = dummy.substring(dummy.indexOf("appointment ") + 12).trim();
            return new SearchAppointmentCommand(time);
        }
        else{
            throw new IHospitalException("Please enter whether you want to search for a doctor, patient or appointment");
        }
    }

    public Command parseListCommand(String fullCommand) throws IHospitalException {
        if (fullCommand.contains("list doctor")) {
            return new ListDoctorListCommand();
        } else if (fullCommand.contains("list appointment")) {
            return new ListAppointmentListCommand();
        } else if (fullCommand.contains("list patient")) {
            return new ListPatientListCommand();
        }
        return null;
    }

    public Command parse(String fullCommand) throws IHospitalException {
        if (fullCommand.contains("add")) {
            return parseAddCommand(fullCommand);
        } else if (fullCommand.contains("get") && fullCommand.contains("appointment")) {
            return parseGetAppointment(fullCommand);
        } else if (fullCommand.contains("sort appointment")) {
            return new SortAppointmentByTimeCommand();
        } else if (fullCommand.contains("delete")) {
            return parseDeleteCommand(fullCommand);
        } else if (fullCommand.contains("list")) {
            return parseListCommand(fullCommand);
        } else if (fullCommand.contains("help")) {
            return new HelpCommand();
        } else if (fullCommand.contains("search")) {
            return parseSearchCommand(fullCommand);
        } else {
            throw new IHospitalException("Invalid command given");
        }
    }
}


