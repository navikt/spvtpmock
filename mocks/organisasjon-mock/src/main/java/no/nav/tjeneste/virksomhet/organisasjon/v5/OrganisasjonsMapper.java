package no.nav.tjeneste.virksomhet.organisasjon.v5;

import java.util.Arrays;

import no.nav.foreldrepenger.fpmock2.felles.ConversionUtils;
import no.nav.foreldrepenger.fpmock2.testmodell.organisasjon.OrganisasjonDetaljerModell;
import no.nav.foreldrepenger.fpmock2.testmodell.organisasjon.OrganisasjonModell;
import no.nav.tjeneste.virksomhet.organisasjon.v5.informasjon.Organisasjon;
import no.nav.tjeneste.virksomhet.organisasjon.v5.informasjon.OrganisasjonsDetaljer;
import no.nav.tjeneste.virksomhet.organisasjon.v5.informasjon.UstrukturertNavn;
import no.nav.tjeneste.virksomhet.organisasjon.v5.informasjon.Virksomhet;

public class OrganisasjonsMapper {
    public OrganisasjonsMapper() {
    }

    public static Organisasjon mapOrganisasjonFraModell(OrganisasjonModell modell) {
        Organisasjon organisasjon = new Virksomhet(); // TODO: Skulle hatt mulighet til å returnere forskjellige sub-typer ?
        organisasjon.setOrgnummer(modell.getOrgnummer());
        UstrukturertNavn ustrukturertNavn = new UstrukturertNavn();
        ustrukturertNavn.getNavnelinje().addAll(Arrays.asList(modell.getNavn().getNavnelinje()));
        organisasjon.setNavn(ustrukturertNavn);
        organisasjon.setOrganisasjonDetaljer(mapOrganisasjonDetaljerFraModell(modell.getOrganisasjonDetaljer()));
        return organisasjon;
    }

    public static OrganisasjonsDetaljer mapOrganisasjonDetaljerFraModell(OrganisasjonDetaljerModell detaljer) {
        OrganisasjonsDetaljer organisasjonsDetaljer = new OrganisasjonsDetaljer();
        if (!(null == detaljer)) {
            if (!(null == detaljer.getRegistreringsDato())) {
                organisasjonsDetaljer.setRegistreringsDato(ConversionUtils.convertToXMLGregorianCalendar(detaljer.getRegistreringsDato()));
            }
            if (!(null == detaljer.getDatoSistEndret())) {
                organisasjonsDetaljer.setDatoSistEndret(ConversionUtils.convertToXMLGregorianCalendar(detaljer.getDatoSistEndret()));
            }
        }
        return organisasjonsDetaljer;
    }
}
