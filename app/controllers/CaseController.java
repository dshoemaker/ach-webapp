package controllers;

    import constants.ModeConst;
    import models.BaseCase;
    import models.Reclamation;
    import play.Routes;
    import play.data.Form;
    import play.mvc.Controller;
    import play.mvc.Result;
    import play.Logger;
    import views.html.cases.main_menu.case_view;
    import views.html.cases.new_case_form.*;
    import views.html.cases.main_menu.list;

    import java.util.List;
    import java.text.SimpleDateFormat;
    import java.util.Date;

    /**
     * Created by Dan on 2/25/2016.
     */
    public class CaseController extends Controller {

    private static Logger.ALogger logger = Logger.of(CaseController.class);

    public Result list() {
        List<Reclamation> allCases = Reclamation.findAll(BaseCase.Status.OPEN);
        Form<BaseCase> aCaseForm = Form.form(BaseCase.class);
        return ok(list.render(allCases, aCaseForm));
    }

    public Result addCase() {
        Form<BaseCase> aCaseForm = Form.form(BaseCase.class).bindFromRequest();
        String case_type = aCaseForm.field("caseType").value();

        switch(case_type) {
            case "Government Reclamations":
                Form<Reclamation> detailsForm = Form.form(Reclamation.class);
                return ok(reclamation_subtype_panel.render(detailsForm, case_type, ModeConst.ADD, currentDate()));
            case "POA":
                return ok(dummy_template.render(case_type, ModeConst.ADD));
            case "Returns":
                return ok(dummy_template.render(case_type, ModeConst.ADD));
            case "Reversals/Deletions":
                return ok(dummy_template.render(case_type, ModeConst.ADD));
            case "Unresolved/Dishonored Returns":
                return ok(dummy_template.render(case_type, ModeConst.ADD));
            default:
                // should never reach here due to client side validation
                logger.debug("Empty Case type selected");
                return badRequest();
        }
    }


    public Result save(String case_type, String mode) {
        Form<Reclamation> aCaseForm = Form.form(Reclamation.class).bindFromRequest();
        if (aCaseForm.hasErrors()) {
            logger.debug("Case Form Error");
            return badRequest(reclamation_subtype_panel.render(aCaseForm, case_type, mode, currentDate()));
        }
        Reclamation ach_case = aCaseForm.get();
        if(ach_case != null) {
            if (ModeConst.ADD.equals(mode)) {
                if (Reclamation.exists(ach_case.id)) {
                    flash("errorsFound", "Case ID already exists");
                    return badRequest(reclamation_subtype_panel.render(aCaseForm, case_type, mode, currentDate()));
                }
                //successful form and object binding
                ach_case.save();
                return redirect(routes.CaseController.list());
            }
            else if (mode.startsWith(ModeConst.EDIT)) {
                ach_case.update();
                return redirect(routes.CaseController.list());
            }
        }
        return badRequest(reclamation_subtype_panel.render(aCaseForm, case_type, mode, currentDate()));
    }

    public Result editCase(Integer caseId) {
        Reclamation ach_case = Reclamation.retrieve(caseId);
        Form<Reclamation> caseForm = Form.form(Reclamation.class).fill(ach_case);
        return ok(reclamation_subtype_panel.render(caseForm, ach_case.caseType, ModeConst.EDIT, currentDate()));
    }

    public Result deleteCase(Integer caseId) {
        Reclamation rec = Reclamation.retrieve(caseId);
        if (rec == null) {
            return notFound(caseId + " is not on file");
        }
        rec.delete();
        return redirect(routes.CaseController.list());
    }

    public Result findCase(Integer caseId) {
        Reclamation rec = Reclamation.findById(caseId);
        if(rec == null) {
            return badRequest();
        }
        return ok(case_view.render(rec));
    }

    public Result jsCaseRoutes() {
        return ok(Routes.javascriptRouter("jsCaseRoutes",
                routes.javascript.CaseController.findCase()));
    }


    private String currentDate() {
        SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");
        String today = mdyFormat.format(new Date());
        return today;
    }
}
