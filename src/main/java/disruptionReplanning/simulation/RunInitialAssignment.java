package disruptionReplanning.simulation;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.config.groups.PlansCalcRouteConfigGroup;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.config.groups.StrategyConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule;
import org.matsim.core.scenario.ScenarioUtils;

public class RunInitialAssignment {

    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        config.controler().setLastIteration(10);
        config.controler().setMobsim("qsim");
        config.controler().setWritePlansInterval(config.controler().getLastIteration());
        config.controler().setWriteEventsInterval(config.controler().getLastIteration());
        config.controler().setOutputDirectory("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/initialAssignment");
        config.controler().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );

        config.qsim().setEndTime(13*3600);
        config.qsim().setTrafficDynamics(QSimConfigGroup.TrafficDynamics.withHoles);
        config.qsim().setFlowCapFactor(0.1);
        config.qsim().setStorageCapFactor(0.1);
        config.qsim().setStuckTime(10);
        config.qsim().setNumberOfThreads(16);
        config.global().setNumberOfThreads(16);
        config.parallelEventHandling().setNumberOfThreads(16);
        //config.qsim().setUsingThreadpool(false);

        config.network().setInputFile("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/network/mergedNetwork2018.xml");
        config.plans().setInputFile("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/10percent_plans_ptPassenger.xml.gz");
        config.transit().setUseTransit(true);
        config.transit().setTransitScheduleFile("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/network/schedule2018.xml");
        config.transit().setVehiclesFile("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/network/vehicles2018.xml");
        //config.transit().setTransitModes(Sets.newHashSet("pt"));
        //config.vspExperimental().setWritingOutputEvents(true);


        PlanCalcScoreConfigGroup.ActivityParams home = new PlanCalcScoreConfigGroup.ActivityParams("home");
        home.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(home);

        PlanCalcScoreConfigGroup.ActivityParams HBW = new PlanCalcScoreConfigGroup.ActivityParams("HBW");
        HBW.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(HBW);

        PlanCalcScoreConfigGroup.ActivityParams HBE = new PlanCalcScoreConfigGroup.ActivityParams("HBE");
        HBE.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(HBE);

        PlanCalcScoreConfigGroup.ActivityParams NHBW = new PlanCalcScoreConfigGroup.ActivityParams("NHBW");
        NHBW.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(NHBW);

        PlanCalcScoreConfigGroup.ActivityParams HBS = new PlanCalcScoreConfigGroup.ActivityParams("HBS");
        HBS.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(HBS);

        PlanCalcScoreConfigGroup.ActivityParams HBR = new PlanCalcScoreConfigGroup.ActivityParams("HBR");
        HBR.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(HBR);

        PlanCalcScoreConfigGroup.ActivityParams HBO = new PlanCalcScoreConfigGroup.ActivityParams("HBO");
        HBO.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(HBO);

        PlanCalcScoreConfigGroup.ActivityParams NHBO = new PlanCalcScoreConfigGroup.ActivityParams("NHBO");
        NHBO.setTypicalDuration(1 * 60 * 60);
        config.planCalcScore().addActivityParams(NHBO);


//        PlansCalcRouteConfigGroup.ModeRoutingParams car = new PlansCalcRouteConfigGroup.ModeRoutingParams("car");
//        car.setTeleportedModeFreespeedFactor(1.0);
//        config.plansCalcRoute().addModeRoutingParams(car);

//        PlansCalcRouteConfigGroup.ModeRoutingParams pt = new PlansCalcRouteConfigGroup.ModeRoutingParams("pt");
//        pt.setTeleportedModeFreespeedFactor(1.0);
//        config.plansCalcRoute().addModeRoutingParams(pt);

        PlansCalcRouteConfigGroup.ModeRoutingParams bike = new PlansCalcRouteConfigGroup.ModeRoutingParams("bike");
        bike.setBeelineDistanceFactor(2.0);
        bike.setTeleportedModeSpeed(12 / 3.6);
        config.plansCalcRoute().addModeRoutingParams(bike);

        PlansCalcRouteConfigGroup.ModeRoutingParams walk = new PlansCalcRouteConfigGroup.ModeRoutingParams("walk");
        walk.setBeelineDistanceFactor(2.0);
        walk.setTeleportedModeSpeed(4 / 3.6);
        config.plansCalcRoute().addModeRoutingParams(walk);


        // define strategies:
        {
            StrategyConfigGroup.StrategySettings strat = new StrategyConfigGroup.StrategySettings();
            strat.setStrategyName(DefaultPlanStrategiesModule.DefaultStrategy.ReRoute.toString());
            strat.setWeight(0.5);
            config.strategy().addStrategySettings(strat);
        }
        {
            StrategyConfigGroup.StrategySettings strat = new StrategyConfigGroup.StrategySettings();
            strat.setStrategyName(DefaultPlanStrategiesModule.DefaultSelector.ChangeExpBeta.toString());
            strat.setWeight(0.1);
            config.strategy().addStrategySettings(strat);
        }

        config.strategy().setFractionOfIterationsToDisableInnovation(0.8);
        config.strategy().setMaxAgentPlanMemorySize(4);


        Scenario scenario = ScenarioUtils.loadScenario(config) ;

        Controler controler = new Controler( scenario ) ;
        controler.run();


    }

}
