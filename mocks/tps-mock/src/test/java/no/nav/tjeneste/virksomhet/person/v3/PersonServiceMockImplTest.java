package no.nav.tjeneste.virksomhet.person.v3;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import no.nav.foreldrepenger.fpmock2.testmodell.repo.Testscenario;
import no.nav.foreldrepenger.fpmock2.testmodell.repo.impl.BasisdataProviderFileImpl;
import no.nav.foreldrepenger.fpmock2.testmodell.repo.impl.DelegatingTestscenarioRepository;
import no.nav.foreldrepenger.fpmock2.testmodell.repo.impl.DelegatingTestscenarioTemplateRepository;
import no.nav.foreldrepenger.fpmock2.testmodell.repo.impl.TestscenarioRepositoryImpl;
import no.nav.foreldrepenger.fpmock2.testmodell.repo.impl.TestscenarioTemplateRepositoryImpl;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.person.v3.binding.HentPersonSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.AktoerId;
import no.nav.tjeneste.virksomhet.person.v3.informasjon.Informasjonsbehov;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonRequest;
import no.nav.tjeneste.virksomhet.person.v3.meldinger.HentPersonResponse;

public class PersonServiceMockImplTest {

    static TestscenarioTemplateRepositoryImpl templateRepositoryImpl;
    static DelegatingTestscenarioTemplateRepository templateRepository;
    static DelegatingTestscenarioRepository testScenarioRepository;
    static PersonServiceMockImpl tps;

    @BeforeClass
    public static void initScenarios() throws IOException {
        System.setProperty("scenarios.dir", "../../model/scenarios/");
        templateRepositoryImpl = TestscenarioTemplateRepositoryImpl.getInstance();
        templateRepositoryImpl.load();
        templateRepository = new DelegatingTestscenarioTemplateRepository(templateRepositoryImpl);
        testScenarioRepository = new DelegatingTestscenarioRepository(TestscenarioRepositoryImpl.getInstance(BasisdataProviderFileImpl.getInstance()));
        tps = new PersonServiceMockImpl(testScenarioRepository);

    }

    @Test
    public void skalReturnereAddresseVedBehov() throws IOException, HentPersonPersonIkkeFunnet, HentPersonSikkerhetsbegrensning {
        Testscenario scenario = lastScenario("50");


        HentPersonRequest req = new HentPersonRequest();
        AktoerId aktørId = new AktoerId();
        aktørId.setAktoerId(scenario.getPersonopplysninger().getSøker().getAktørIdent());
        req.setAktoer(aktørId);
        req.withInformasjonsbehov(Informasjonsbehov.ADRESSE);

        HentPersonResponse resp = tps.hentPerson(req);

        Assert.assertNotNull(resp.getPerson().getBostedsadresse());
    }

    @Test
    public void skalIkkeReturnereAddresseUtenBehov() throws IOException, HentPersonPersonIkkeFunnet, HentPersonSikkerhetsbegrensning {
        Testscenario scenario = lastScenario("50");

        HentPersonRequest req = new HentPersonRequest();
        AktoerId aktørId = new AktoerId();
        aktørId.setAktoerId(scenario.getPersonopplysninger().getSøker().getAktørIdent());
        req.setAktoer(aktørId);

        HentPersonResponse resp = tps.hentPerson(req);

        Assert.assertNull(resp.getPerson().getBostedsadresse());
    }


    private static Testscenario lastScenario(String templateKey) {
        return testScenarioRepository.opprettTestscenario(templateRepositoryImpl.finn("50"));
    }

}
