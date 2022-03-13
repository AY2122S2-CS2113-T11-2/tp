package tp;

public abstract class Command {
    /**
     * A method to check if the command is an ExitCommand
     *
     * @return A boolean value indicates whether the command is an ExitCommand
     */
    public boolean isExit() {
        return false;
    }

    /**
     * A method to execute the command
     * @param doctorList duke's task list
     * @param ui duke's ui
     * @param storage duke's storage
     * @return A string generated by ui
     * @throws IHospitalException
     */
    public abstract void execute(DoctorList doctorList, PatientList patientList,
                                   AppointmentList appointmentList, Ui ui, Storage storage) throws IHospitalException;
}
