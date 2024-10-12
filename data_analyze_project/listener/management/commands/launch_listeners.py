from django.core.management.base import BaseCommand
from .report_creation_listener import ReportCreationListener


class Command(BaseCommand):
    help = 'Launches Listeners : Kafka'

    def handle(self, *args, **options):
        td = ReportCreationListener()
        td.start()
        self.stdout.write("Started Consumer Thread")
